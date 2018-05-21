package org.drombler.commons.client.startup.main.cli;

public abstract class AbstractCommandLineSwitch implements CommandLineSwitch{

    private final String commandLineSwitch;

    public AbstractCommandLineSwitch(String commandLineSwitch) {
        this.commandLineSwitch = commandLineSwitch;
    }

        @Override
    public boolean supportsSwitch(String commandLineSwitch) {
        return commandLineSwitch.equals(this.commandLineSwitch);
    }
    
    protected void checkConsumeSwitchArgs(String commandLineSwitch, String[] followUpArgs) throws IllegalArgumentException {
        if (!supportsSwitch(commandLineSwitch)) {
            throw new IllegalArgumentException("Unsupported switch: " + commandLineSwitch);
        }
        if (followUpArgs.length != getNumFollowUpArgs()) {
            throw new IllegalArgumentException(getNumFollowUpArgs() + "followUpArgs expected!");
        }
    }
}
