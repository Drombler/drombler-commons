package org.drombler.commons.client.startup.main.cli;

import java.util.Set;

/**
 * An abstract base class for {@link CommandLineSwitch}es.
 */
public abstract class AbstractCommandLineSwitch implements CommandLineSwitch{

    private final Set<String> commandLineSwitches;

    /**
     * Creates a new instance of this class.
     *
     * @param commandLineSwitches a single supported command line switch
     */
    public AbstractCommandLineSwitch(Set<String> commandLineSwitches) {
        this.commandLineSwitches = commandLineSwitches;
    }

    /**
     * {@inheritDoc }
     */
        @Override
    public boolean supportsSwitch(String commandLineSwitch) {
            return commandLineSwitches.contains(commandLineSwitch);
    }

    /**
     * Checks if the switch args can be consumed.
     *
     * @param commandLineSwitch the command line switch
     * @param followUpArgs the follow-up arguments
     * @throws IllegalArgumentException
     */
    protected void checkConsumeSwitchArgs(String commandLineSwitch, String[] followUpArgs) throws IllegalArgumentException {
        if (!supportsSwitch(commandLineSwitch)) {
            throw new IllegalArgumentException("Unsupported switch: " + commandLineSwitch);
        }
        if (followUpArgs.length != getNumFollowUpArgs()) {
            throw new IllegalArgumentException(getNumFollowUpArgs() + "followUpArgs expected!");
        }
    }
}
