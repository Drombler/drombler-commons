package org.drombler.commons.client.startup.main.cli;

/**
 *
 * @author puce
 */
public class InstallDirSwitch extends AbstractCommandLineSwitch implements CommandLineSwitch {

    /**
     * Switch for specifying the install directory.
     */
    public static final String INSTALL_DIR_SWITCH = "--installdir";

    private String installDir;

    public InstallDirSwitch() {
        super(INSTALL_DIR_SWITCH);
    }
    
   

    @Override
    public int getNumFollowUpArgs() {
        return 1;
    }

    @Override
    public void consumeSwitch(String commandLineSwitch, String[] followUpArgs) {
        checkConsumeSwitchArgs(commandLineSwitch, followUpArgs);
        this.installDir = followUpArgs[0];
    }


    @Override
    public String getHelpText() {
        return "[--installdir <install-directory>]";
    }

    /**
     * @return the userDir
     */
    public String getInstallDir() {
        return installDir;
    }

}
