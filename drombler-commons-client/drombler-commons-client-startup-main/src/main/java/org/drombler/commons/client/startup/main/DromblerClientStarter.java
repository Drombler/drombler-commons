package org.drombler.commons.client.startup.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import org.drombler.commons.client.startup.main.cli.CommandLineArgs;
import org.drombler.commons.client.startup.main.impl.singleinstance.SingleInstanceStarter;

/**
 * An abstract base class for a client starter using the Drombler Client Startup Framework.
 *
 * @param <T> the type of the {@link DromblerClientConfiguration}
 */
public abstract class DromblerClientStarter<T extends DromblerClientConfiguration> {

    /**
     * The main method to execute the client starter using a simple implementation.
     *
     * @param args the command line args
     * @throws URISyntaxException
     * @throws IOException
     * @throws MissingPropertyException
     * @throws InterruptedException
     * @throws Exception
     */
    public static void main(String[] args) throws URISyntaxException, IOException, MissingPropertyException, Exception {
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

    /**
     * Creates a new instance of this class.
     *
     * @param configuration the client configuration
     */
    public DromblerClientStarter(T configuration) {
        this.configuration = configuration;
        this.singleInstanceStarter = new SingleInstanceStarter(configuration);
        addAdditionalStarters(singleInstanceStarter);// first starter
    }

    /**
     * Adds additional {@link BootServiceStarter}s to the list of starters to execute. Only active starters will be added to the effective starters list.
     *
     * @param additionalStarters additional starters
     * @see BootServiceStarter#isActive()
     */
    protected final void addAdditionalStarters(BootServiceStarter... additionalStarters) {
        Arrays.stream(additionalStarters)
                .filter(BootServiceStarter::isActive)
                .forEach(starters::add);
    }

    /**
     * Initialzies this client starter.
     *
     * @return true, if initialization was successful, else false
     * @throws Exception
     * @see #start()
     * @see #stop()
     */
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

    /**
     * Starts this initialized client starter.
     *
     * @see #init()
     * @see #stop()
     */
    public void start() {
        starters.stream().
                map(this::createBootServiceStarterThread).
                forEach(Thread::start);
        if (singleInstanceStarter.isActive()) {
            singleInstanceStarter.setApplicationInstanceListener(getApplicationInstanceListener());
        }
    }

    /**
     * Stops this client starter.
     *
     * @see #init()
     * @see #start()
     */
    public synchronized void stop() {
        if (!stopped) {
            stopped = true;
            starters.forEach(this::stopBootServiceStarter);
        }
    }

    /**
     * Gets the application instance listener.
     *
     * @return the application instance listener
     */
    protected abstract ApplicationInstanceListener getApplicationInstanceListener();

    private void startBootServiceStarter(BootServiceStarter starter) {
        try {
            registerShutdownHook(starter);
            starter.startAndWait();
        } catch (Exception ex) {
            logError("Error starting starter " + starter.getName() + ": " + ex.getMessage(), ex);
        }
    }

    /**
     * Registers a shutdown hook.
     *
     * @param starter a boot service starter
     * @see Runtime#addShutdownHook(java.lang.Thread)
     */
    protected void registerShutdownHook(BootServiceStarter starter) {
        Runtime.getRuntime().addShutdownHook(createBootServiceStarterShutdownHookThread(starter));
    }

    private void stopBootServiceStarter(BootServiceStarter starter) {
        try {
            if (starter.isRunning()) {
                starter.stop();
            }
        } catch (Exception ex) {
            logError("Error stopping starter " + starter.getName() + ": " + ex.getMessage(), ex);
        }
    }

    private Thread createBootServiceStarterThread(BootServiceStarter starter) {
        Thread starterThread = Executors.defaultThreadFactory().newThread(() -> startBootServiceStarter(starter));
        starterThread.setDaemon(true);
        starterThread.setName("Boot Service Starter Thread - " + starter.getName());
        return starterThread;
    }

    private Thread createBootServiceStarterShutdownHookThread(BootServiceStarter starter) {
        Thread starterThread = Executors.defaultThreadFactory().newThread(() -> stopBootServiceStarter(starter));
        starterThread.setName(starter.getName() + " Shutdown Hook");
        return starterThread;
    }

    private void logError(String message, Exception ex) {
        // TODO: replace with SLF4J Logger once available on startup classpath of OSGi applications as well
        // Note: the message format is different!
        System.err.println(message);
        ex.printStackTrace();
    }

    /**
     * Gets the client configuration.
     *
     * @return the client configuration
     */
    public T getConfiguration() {
        return configuration;
    }
}
