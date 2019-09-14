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
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.startup.main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.drombler.commons.client.startup.main.cli.CommandLineArgs;

/**
 * The client configuration. Subclasses may add additional properties or change the configuration such as the application layout.
 */
public class DromblerClientConfiguration {

    /**
     * The jar URI prefix "jar:"
     */
    private static final String FULL_JAR_URI_PREFIX = "jar:";
    /**
     * Length of the jar URI prefix "jar:"
     */
    private static final int FULL_JAR_URI_PREFIX_LENGTH = 4;

    /**
     * The property name used to specify an URL to the system property file.
     *
     */
    public static final String SYSTEM_PROPERTIES_PROP = "system.properties.file";

    /**
     * The default name used for the system properties file.
     *
     */
    public static final String SYSTEM_PROPERTIES_FILE_NAME = "system.properties";

    /**
     * Name of the configuration directory.
     */
    public static final String CONFIG_DIRECTORY_NAME = "conf";

    /**
     * The property name used to specify an URL to the configuration property file to be used for the created the framework instance.
     *
     */
    public static final String CONFIG_PROPERTIES_PROP = "config.properties.file";
    /**
     * The default name used for the configuration properties file.
     *
     */
    public static final String CONFIG_PROPERTIES_FILE_NAME = "config.properties";

    /**
     * The property name of the user dir property in the config.properties file expected at {@code <install-dir>/conf}.
     */
    public static final String USER_DIR_PROPERTY = "drombler.application.userdir";

    private final Path installDirPath;
    private final Path installConfigDirPath;
    private final Path userDirPath;
    private final Path userConfigDirPath;

    private final Properties userConfigProps;
    private final CommandLineArgs commandLineArgs;
    private final ApplicationConfiguration applicationConfig;

    /**
     * Creates a new instance of this class.
     *
     * @param commandLineArgs the command line args
     * @throws URISyntaxException
     * @throws IOException
     * @throws MissingPropertyException
     */
    public DromblerClientConfiguration(CommandLineArgs commandLineArgs) throws URISyntaxException, IOException,
            MissingPropertyException {
        this.commandLineArgs = commandLineArgs;
        if (commandLineArgs.getInstallDirSwitch().getInstallDir() != null) {
            this.installDirPath = Paths.get(commandLineArgs.getInstallDirSwitch().getInstallDir());
        } else {
            Path mainJarPath = determineMainJarPath();
            this.installDirPath = determineInstallDirPath(mainJarPath);
        }
        System.out.println("Installation dir: " + installDirPath);
        this.installConfigDirPath = installDirPath.resolve(CONFIG_DIRECTORY_NAME);

        loadSystemProperties(getInstallDirPath());

        Properties defaultConfigProps = loadDefaultConfigProps();
        Properties installConfigProps = createInstallConfigProps(defaultConfigProps, commandLineArgs);
        resolveProperties(installConfigProps);

        this.userDirPath = determineUserDirPath(installConfigProps);
        if (!Files.exists(userDirPath)) {
            Files.createDirectories(userDirPath);
        }
        System.out.println("User dir: " + userDirPath);
        this.userConfigProps = new Properties(installConfigProps);
        loadConfigProperties(userConfigProps, userDirPath);

        resolveProperties(userConfigProps);
        copySystemProperties(userConfigProps);

        this.userConfigDirPath = userDirPath.resolve(CONFIG_DIRECTORY_NAME);
        if (!Files.exists(userConfigDirPath)) {
            Files.createDirectories(userConfigDirPath);
        }

        this.applicationConfig = new ApplicationConfiguration();
    }

    private Properties createInstallConfigProps(Properties defaultConfigProps, CommandLineArgs commandLineArgs) throws
            IOException {
        Properties installConfigProps = new Properties(defaultConfigProps);
        loadConfigProperties(installConfigProps, getInstallDirPath());
        overrideInstallConfigProps(installConfigProps, commandLineArgs);
        return installConfigProps;
    }

    private Path determineMainJarPath() throws URISyntaxException {
        Class<DromblerClientStarter> type = DromblerClientStarter.class;
        String jarResourceURIString = type.getResource("/" + type.getName().replace(".", "/") + ".class").toURI().
                toString();
        int endOfJarPathIndex = jarResourceURIString.indexOf("!/");
        String mainJarURIString = endOfJarPathIndex >= 0 ? jarResourceURIString.substring(0, endOfJarPathIndex)
                : jarResourceURIString;
        if (mainJarURIString.startsWith(FULL_JAR_URI_PREFIX)) {
            mainJarURIString = mainJarURIString.substring(FULL_JAR_URI_PREFIX_LENGTH);
        }
        Path mainJarPath = Paths.get(URI.create(mainJarURIString));
        return mainJarPath;
    }

    /**
     * Determines the installation directory path of this application. The default implementation expects the following application installation layout: {@code <install-dir>/lib/<jar>}
     *
     * @param mainJarPath the path to the JAR file containing the main starter
     * @return the installation directory path of this application
     */
    protected Path determineInstallDirPath(Path mainJarPath) {
        // <install-dir>/lib/<jar>
        return mainJarPath.getParent().getParent();
    }

    /**
     * Loads and sets the system properties.
     *
     * @param rootDirPath the root dir path
     * @throws MalformedURLException
     * @throws IOException
     */
    protected void loadSystemProperties(Path rootDirPath) throws MalformedURLException, IOException {
        Properties systemProps = new Properties();
        loadProperties(systemProps, SYSTEM_PROPERTIES_PROP, rootDirPath, SYSTEM_PROPERTIES_FILE_NAME);

        resolveProperties(systemProps);

        systemProps.stringPropertyNames().
                forEach(propertyName -> System.setProperty(propertyName, systemProps.getProperty(propertyName)));

    }

    private void loadConfigProperties(Properties configProps, Path rootDirPath) throws MalformedURLException,
            IOException {
        loadProperties(configProps, CONFIG_PROPERTIES_PROP, rootDirPath, CONFIG_PROPERTIES_FILE_NAME);
    }

    private void loadProperties(Properties props, String systemPropertyName, Path rootDirPath, String propertiesFileName)
            throws IOException, MalformedURLException {
        String custom = System.getProperty(systemPropertyName);
        URL propURL = custom != null ? new URL(custom) : rootDirPath.resolve(CONFIG_DIRECTORY_NAME).resolve(
                propertiesFileName).toUri().toURL();

        try (InputStream is = propURL.openConnection().getInputStream()) {
            props.load(is);
        } catch (FileNotFoundException ex) {
            // Ignore file not found.
        } catch (IOException ex) {
            System.err.println(
                    "Main: Error loading system properties from " + propURL);
            throw ex;
        }
    }

    /**
     * Loads the default configuration properties.
     *
     * @return the default configuration properties
     * @throws IOException
     */
    protected Properties loadDefaultConfigProps() throws IOException {
        Properties props = new Properties();
        try (InputStream is = DromblerClientConfiguration.class.getResourceAsStream("config.properties")) {
            props.load(is);
        }
        return props;
    }

    private void overrideInstallConfigProps(Properties installConfigProps, CommandLineArgs commandLineArgs) {
        if (commandLineArgs.getUserDirSwitch().getUserDir() != null) {
            installConfigProps.setProperty(USER_DIR_PROPERTY, commandLineArgs.getUserDirSwitch().getUserDir());
        }
    }

    private Path determineUserDirPath(Properties installConfigProps) throws MissingPropertyException {
        String userDirName = installConfigProps.getProperty(USER_DIR_PROPERTY);
        if (userDirName == null) {
            throw new MissingPropertyException("Undefined property: " + USER_DIR_PROPERTY);
        }

        return Paths.get(userDirName);
    }

    /**
     * Copies and sets some additional system properties. The default implementation does not copy any additional system properties.
     *
     * @param configProps the source properties to copy to
     */
    protected void copySystemProperties(Properties configProps) {
        // does currently nothing by default
    }

    /**
     * Resolves properties with variables. The default implementation does nothing currently.
     *
     * @param configProps the properties to resolve
     */
    protected void resolveProperties(Properties configProps) {
        // does currently nothing by default
        // TODO: is property substitution generally required or only for Drombler FX applications?
    }

    /**
     * Gets the installation directory path of this application.
     *
     * @return the installation directory path of this application
     */
    public final Path getInstallDirPath() {
        return installDirPath;
    }

    /**
     * Gets the path to the configuration directory in the installation directory of this application.
     *
     * @return the path to the configuration directory in the installation directory of this application
     */
    public final Path getInstallConfigDirPath() {
        return installConfigDirPath;
    }

    /**
     * Gets the user directory path.
     *
     * @return the user directory path
     */
    public final Path getUserDirPath() {
        return userDirPath;
    }

    /**
     * Gets the path to the configuration directory in the user directory of the current user for this application.
     *
     * @return the path to the configuration directory in the user directory of the current user for this application
     */
    public final Path getUserConfigDirPath() {
        return userConfigDirPath;
    }

    /**
     * Gets the user configuration properties.
     *
     * @return the user configuration properties
     */
    public Properties getUserConfigProps() {
        return userConfigProps;
    }

    /**
     * Gets the command line args.
     *
     * @return the command line args
     */
    public CommandLineArgs getCommandLineArgs() {
        return commandLineArgs;
    }

    /**
     * Gets the application configuration packaged with the application.
     *
     * @return the application configuration
     */
    public ApplicationConfiguration getApplicationConfig() {
        return applicationConfig;
    }

}
