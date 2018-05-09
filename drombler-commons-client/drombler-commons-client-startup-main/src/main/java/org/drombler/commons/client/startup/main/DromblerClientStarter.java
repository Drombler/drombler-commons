package org.drombler.commons.client.startup.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import org.drombler.commons.client.startup.main.cli.CommandLineArgs;
import org.drombler.commons.client.startup.main.impl.singleinstance.SingleInstanceStarter;

public abstract class DromblerClientStarter<T extends DromblerClientConfiguration> {

    public static void main(String[] args) throws URISyntaxException, IOException,
            MissingPropertyException, InterruptedException, Exception {
        CommandLineArgs commandLineArgs = CommandLineArgs.parseCommandLineArgs(args);
        DromblerClientStarter<DromblerClientConfiguration> main = new DromblerClientStarter<DromblerClientConfiguration>(new DromblerClientConfiguration(commandLineArgs)) {
            @Override
            protected ApplicationInstanceListener getApplicationInstanceListener() {
                return additionalArgs -> {
                  // additionalArgs not handled
                };
            }

        };
        if (main.init()) {
            main.start();
        }
    }

    private final SingleInstanceStarter singleInstanceStarter;
    private final List<BootServiceStarter> starters = new ArrayList<>();
    private final T configuration;
    private boolean stopped = false;

    public DromblerClientStarter(T configuration) {
        this.configuration = configuration;
        this.singleInstanceStarter = new SingleInstanceStarter(configuration);
        addAdditionalStarters(singleInstanceStarter);// first starter
    }

    protected final void addAdditionalStarters(BootServiceStarter... additionalStarters) {
        Arrays.stream(additionalStarters)
                .filter(BootServiceStarter::isActive)
                .forEach(starters::add);
    }

    public boolean init() throws Exception {
        boolean initialized = true;
        for (BootServiceStarter starter : starters) {
            if (!starter.init() && starter.isRequired()) {
                initialized = false;
                break;
            }
        }
        return initialized;
    }

    public void start() {
        starters.stream().
                map((BootServiceStarter starter) -> {
                    Thread starterThread = Executors.defaultThreadFactory().
                            newThread(() -> {
                                try {
                                    registerShutdownHook(starter);
                                    starter.startAndWait();
                                } catch (Exception ex) {
                                    logError(ex);
                                }
                            });
                    starterThread.setDaemon(true);
                    starterThread.setName("Boot Service Starter Thread - " + starter.getName());
                    return starterThread;
                }).
                forEach(Thread::start);
        if (singleInstanceStarter.isActive()) {
            singleInstanceStarter.setApplicationInstanceListener(getApplicationInstanceListener());
        }
    }

    protected abstract ApplicationInstanceListener getApplicationInstanceListener();

    protected void registerShutdownHook(BootServiceStarter starter) {
        Runtime.getRuntime().
                addShutdownHook(new Thread(starter.getName() + " Shutdown Hook") {
                    @Override
                    public void run() {
                        try {
                            if (starter.isRunning()) {
                                starter.stop();
                            }
                        } catch (Exception ex) {
                            System.err.println("Error stopping starter " + starter.getName() + ": " + ex);
                        }
                    }
                });
    }

    public synchronized void stop() {
        if (!stopped) {
            stopped = true;
            starters.stream().
                    forEach((BootServiceStarter starter) -> {
                        try {
                            starter.stop();
                        } catch (Exception ex) {
                            logError(ex);
                        }
                    });
        }
    }

    private void logError(Exception ex) {
        // TODO: replace with SLF4J Logger once available on startup classpath of OSGi applications as well
        // Note: the message format is different!
        ex.printStackTrace();
    }

    /**
     * @return the configuration
     */
    public T getConfiguration() {
        return configuration;
    }
}
