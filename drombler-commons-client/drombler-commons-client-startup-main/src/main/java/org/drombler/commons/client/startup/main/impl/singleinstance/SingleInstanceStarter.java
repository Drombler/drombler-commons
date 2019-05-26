/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.startup.main.impl.singleinstance;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;
import org.drombler.commons.client.startup.main.ApplicationInstanceEvent;
import org.drombler.commons.client.startup.main.ApplicationInstanceListener;
import org.drombler.commons.client.startup.main.BootServiceStarter;
import org.drombler.commons.client.startup.main.DromblerClientConfiguration;

/**
 *
 * @author puce
 */
public class SingleInstanceStarter implements BootServiceStarter {

    private static final String SINGLE_INSTANCE_PROPERTIES_FILE_NAME = "singleInstance.properties";
    private static final String PORT_PROPERTY_NAME = "port";
    private static final String DELIMITER = " ";
    private static final String ENCODING = "UTF-8";

    private final DromblerClientConfiguration configuration;
    private ServerSocket serverSocket;

    public SingleInstanceStarter(DromblerClientConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public String getName() {
        return "Single Instance Application Starter";
    }

    @Override
    public boolean isActive() {
        return configuration.getApplicationConfig().isSingleInstanceApplication();
    }

    private ApplicationInstanceListener applicationInstanceListener;

    @Override
    public boolean init() throws IOException {
        boolean initialized;
        Path userConfigDir = configuration.getUserConfigDirPath();
        if (!Files.exists(userConfigDir)) {
            Files.createDirectories(userConfigDir);
        }
        Path singleInstancePropertiesPath = userConfigDir.resolve(SINGLE_INSTANCE_PROPERTIES_FILE_NAME);
        if (Files.exists(singleInstancePropertiesPath)) {
            Properties singleInstanceProperties = loadSingleInstanceProperties(singleInstancePropertiesPath);
            int port = Integer.parseInt(singleInstanceProperties.getProperty(PORT_PROPERTY_NAME));
            try {
                tryInitServerSocket(port);
                initialized = true;
            } catch (IOException ex) {
                notifySingleInstance(port);
                initialized = false;
            }
        } else {
            Files.createFile(singleInstancePropertiesPath);
            int port = initialInitServerSocket(configuration.getApplicationConfig().getDefaultSingleInstancePort());
            storeSingleInstanceProperties(singleInstancePropertiesPath, port);
            initialized = true;
        }
        return initialized;
    }

    private int initialInitServerSocket(int defaultPort) {
        int port = defaultPort;
        boolean retry = true;
        while (retry) {
            try {
                tryInitServerSocket(port);
                retry = false;
            } catch (IOException ex) {
                port++;
            }
        }
        return port;
    }

    private Properties loadSingleInstanceProperties(Path singleInstancePropertiesPath) throws IOException {
        Properties singleInstanceProperties = new Properties();
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(singleInstancePropertiesPath))) {
            singleInstanceProperties.load(bis);
        }
        return singleInstanceProperties;
    }

    private void storeSingleInstanceProperties(Path singleInstancePropertiesPath, int port) throws IOException {
        Properties singleInstanceProperties = new Properties();
        singleInstanceProperties.setProperty(PORT_PROPERTY_NAME, Integer.toString(port));
        try (BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(singleInstancePropertiesPath))) {
            singleInstanceProperties.store(bos, "");
        }
    }

    private void tryInitServerSocket(int port) throws IOException {
        this.serverSocket = new ServerSocket(port, 10, getInetAddress());
    }

    private static InetAddress getInetAddress() throws UnknownHostException {
        return InetAddress.getByName(null);
    }

    @Override
    public void startAndWait() {
        listenForOtherInstances();
    }

    private void listenForOtherInstances() {
        boolean socketClosed = false;
        while (!socketClosed) {
            if (serverSocket.isClosed()) {
                socketClosed = true;
            } else {
                try (Socket socket = serverSocket.accept();
                        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String message = in.readLine();
                    List<String> additionalArgs = Arrays.stream(message.split(DELIMITER))
                            .map(additionalArg -> {
                                try {
                                    return URLDecoder.decode(additionalArg, ENCODING);
                                } catch (UnsupportedEncodingException ex) {
                                    // Should not happen here
                                    throw new RuntimeException(ex);
                                }
                            })
                            .collect(Collectors.toList());
                    fireNewInstance(new ApplicationInstanceEvent(this, additionalArgs));
                } catch (UnsupportedEncodingException ex) {
                    System.err.println("Error connecting to local port for single instance notification");
                    ex.printStackTrace();
                } catch (IOException e) {
                    socketClosed = true;
                }
            }
        }
    }

    public void notifySingleInstance(int port) {
        System.out.println("Port is already taken.  Notifying first instance.");
        try (Socket socket = new Socket(getInetAddress(), port);
                OutputStreamWriter writer = new OutputStreamWriter(socket.getOutputStream(), "UTF-8")) {
            for (Iterator<String> argIterator = configuration.getCommandLineArgs().getAdditionalArguments().iterator();
                    argIterator.hasNext();) {
                String additionalArg = argIterator.next();

                Optional<Path> pathArg = toPath(additionalArg);
                if (pathArg.isPresent()) {
                    additionalArg = pathArg.get().toAbsolutePath().toString();
                }

                writer.write(URLEncoder.encode(additionalArg, ENCODING));
                if (argIterator.hasNext()) {
                    writer.write(DELIMITER);
                }
            }
            writer.write("\n");
            System.out.println("Successfully notified first instance.");
        } catch (IOException e1) {
            System.err.println("Error connecting to local port for single instance notification");
            e1.printStackTrace();
        }
    }

    private Optional<Path> toPath(String pathString) {
        try {
            return Optional.of(Paths.get(pathString));
        } catch (InvalidPathException ex1) {
            try {
                URI uri = new URI(pathString);
                return Optional.of(Paths.get(uri));
            } catch (URISyntaxException | NullPointerException | IllegalArgumentException | FileSystemNotFoundException | SecurityException ex2) {
                return Optional.empty();
            }
        }
    }

    public void setApplicationInstanceListener(ApplicationInstanceListener applicationInstanceListener) {
        this.applicationInstanceListener = applicationInstanceListener;
    }

    private void fireNewInstance(ApplicationInstanceEvent event) {
        if (applicationInstanceListener != null) {
            applicationInstanceListener.newInstanceCreated(event);
        }
    }

    @Override
    public void stop() throws Exception {
        if (isRunning()) {
            serverSocket.close();
        }
    }

    @Override
    public boolean isRunning() {
        return serverSocket != null && !serverSocket.isClosed();
    }

    /**
     * Doesn't prevent the whole application from starting, when e.g. no ServerSocket can be created.
     *
     * @return false
     */
    @Override
    public boolean isRequired() {
        return false;
    }

}
