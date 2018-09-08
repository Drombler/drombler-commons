package org.drombler.commons.client.startup.main.cli;

/**
 *
 * @author puce
 */
public class UserDirSwitch extends AbstractCommandLineSwitch implements CommandLineSwitch {

    /**
     * Switch for specifying the user directory.
     */
    public static final String USER_DIR_SWITCH = "--userdir";

    private String userDir;

    public UserDirSwitch() {
        super(USER_DIR_SWITCH);
    }
    
   

    @Override
    public int getNumFollowUpArgs() {
        return 1;
    }

    @Override
    public void consumeSwitch(String commandLineSwitch, String[] followUpArgs) {
        checkConsumeSwitchArgs(commandLineSwitch, followUpArgs);
        this.userDir = followUpArgs[0];
    }


    @Override
    public String getHelpText() {
        return "[--userdir <user-directory>]";
    }

    /**
     * @return the userDir
     */
    public String getUserDir() {
        return userDir;
    }

}
