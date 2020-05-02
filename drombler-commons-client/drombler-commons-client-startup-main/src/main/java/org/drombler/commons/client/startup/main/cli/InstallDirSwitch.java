package org.drombler.commons.client.startup.main.cli;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * The application installation directory switch.<br>
 * <br>
 * Usage: {@code [--installdir <install-directory>]}
 */
public class InstallDirSwitch extends AbstractCommandLineSwitch implements CommandLineSwitch {

    /**
     * Switch for specifying the install directory.
     */
    public static final String INSTALL_DIR_SWITCH = "--installdir";
    private static final Set<String> SUPPORTED_COMMAND_LINE_SWITCHES = new HashSet<>(Arrays.asList(INSTALL_DIR_SWITCH));

    private String installDir;

    /**
     * Creates a new instance of this class.
     */
    public InstallDirSwitch() {
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
        this.installDir = followUpArgs[0];
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String getHelpText() {
        return "[--installdir <install-directory>]";
    }

    /**
     * Gets the provided application installation directory, if this switch was consumed, else null.
     *
     * @return the provided application installation directory, if this switch was consumed, else null
     */
    public String getInstallDir() {
        return installDir;
    }

}
