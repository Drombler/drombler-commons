package org.drombler.commons.client.startup.main.cli;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The application user directory switch.<br>
 * <br>
 * Usage: {@code [--userdir <user-directory>]}
 */
public class UserDirSwitch extends AbstractCommandLineSwitch implements CommandLineSwitch {

    /**
     * Switch for specifying the user directory.
     */
    public static final String USER_DIR_SWITCH = "--userdir";
    private static final Set<String> SUPPORTED_COMMAND_LINE_SWITCHES = new HashSet<>(Arrays.asList(USER_DIR_SWITCH));

    private String userDir;

    /**
     * Creates a new instance of this class.
     */
    public UserDirSwitch() {
        super(SUPPORTED_COMMAND_LINE_SWITCHES);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public int getNumFollowUpArgs() {
        return 1;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void consumeSwitch(String commandLineSwitch, String[] followUpArgs) {
        checkConsumeSwitchArgs(commandLineSwitch, followUpArgs);
        this.userDir = followUpArgs[0];
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getHelpText() {
        return "[--userdir <user-directory>]";
    }

    /**
     * Gets the provided application user directory, if this switch was consumed, else null.
     *
     * @return the provided application user directory, if this switch was consumed, else null
     */
    public String getUserDir() {
        return userDir;
    }

}
