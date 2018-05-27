package org.drombler.commons.client.startup.main.cli;

/**
 *
 * @author puce
 */
public interface CommandLineSwitch {
    boolean supportsSwitch(String commandLineSwitch);
    
    int getNumFollowUpArgs();
    
    void consumeSwitch(String commandLineSwitch, String[] followUpArgs);

    public String getHelpText();
}
