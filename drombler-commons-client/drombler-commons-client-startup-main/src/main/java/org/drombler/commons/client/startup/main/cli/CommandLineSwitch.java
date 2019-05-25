package org.drombler.commons.client.startup.main.cli;

/**
 * A command line switch.
 */
public interface CommandLineSwitch {

    /**
     * Indicates if this switch supports the provided String switch.
     *
     * @param commandLineSwitch the command line switch
     * @return true, if the switch is supported, else false
     */
    boolean supportsSwitch(String commandLineSwitch);

    /**
     * Gets the number of follow-up args required for this switch.
     *
     * @return the number of follow-up args required for this switch
     */
    int getNumFollowUpArgs();

    /**
     * Consumes the switch.
     *
     * @param commandLineSwitch the command line switch
     * @param followUpArgs the required follow-up args.
     */
    void consumeSwitch(String commandLineSwitch, String[] followUpArgs);

    /**
     * Gets a help text for this switch.
     *
     * @return a help text for this switch
     */
    public String getHelpText();
}
