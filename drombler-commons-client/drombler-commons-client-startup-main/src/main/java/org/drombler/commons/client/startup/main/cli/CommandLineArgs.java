/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.startup.main.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The command line arguments.
 */
public class CommandLineArgs {

    private final List<String> additionalArguments;
    private final InstallDirSwitch installDirSwitch;
    private final UserDirSwitch userDirSwitch;

    private CommandLineArgs(InstallDirSwitch installDirSwitch, UserDirSwitch userDirSwitch, List<String> additionalArguments) {
        this.installDirSwitch = installDirSwitch;
        this.userDirSwitch = userDirSwitch;
        this.additionalArguments = additionalArguments;
    }

    /**
     * Parses the command line arguments.
     *
     * @param args the command line arguments to parse
     * @return the command line arguments
     */
    public static CommandLineArgs parseCommandLineArgs(String[] args) {
        List<String> additionalArguments = new ArrayList<>();

        InstallDirSwitch installDirSwitch = new InstallDirSwitch();
        UserDirSwitch userDirSwitch = new UserDirSwitch();
        List<CommandLineSwitch> commandLineSwitches = Arrays.asList(installDirSwitch, userDirSwitch);

        for (int index = 0; index < args.length; index++) {
            boolean argConsumed = false;
            if (additionalArguments.isEmpty()) {
                for (CommandLineSwitch commandLineSwitch : commandLineSwitches) {
                    if (commandLineSwitch.supportsSwitch(args[index])) {
                        handleCommandLineSwitch(commandLineSwitch, args, index);

                        argConsumed = true;
                        index += commandLineSwitch.getNumFollowUpArgs();
                        break;
                    }
                }
            }
            if (!argConsumed) {
                if (args[index].startsWith("-")) {
                    wrongArgument(installDirSwitch, userDirSwitch, args[index]);
                } else {
                    additionalArguments.add(args[index]);
                }
            }
        }

        return new CommandLineArgs(installDirSwitch, userDirSwitch, additionalArguments);
    }

    private static void handleCommandLineSwitch(CommandLineSwitch commandLineSwitch, String[] args, int index) throws IllegalArgumentException {
        if (index + commandLineSwitch.getNumFollowUpArgs() >= args.length) {
            throw new IllegalArgumentException("Usage: " + commandLineSwitch.getHelpText());
        }

        String[] followUpArgs = commandLineSwitch.getNumFollowUpArgs() > 0 ? Arrays.copyOfRange(args, index + 1, index + 1 + commandLineSwitch.getNumFollowUpArgs())
                : new String[]{};

        commandLineSwitch.consumeSwitch(args[index], followUpArgs);
    }

    private static void wrongArgument(InstallDirSwitch installDirSwitch, UserDirSwitch userDirSwitch, String arg) {
        System.out.println("Usage: " + installDirSwitch.getHelpText() + " " + userDirSwitch.getHelpText() + " [filename 1] .. [filenam n]");
        throw new IllegalArgumentException("Unsupported command line argument: " + arg);
    }

    /**
     * Gets the additional arguments.
     *
     * @return the additional arguments
     */
    public List<String> getAdditionalArguments() {
        return additionalArguments;
    }

    /**
     * Gets the application installation directory switch.
     *
     * @return the application installation directory switch
     */
    public InstallDirSwitch getInstallDirSwitch() {
        return installDirSwitch;
    }

    /**
     * Gets the application user directory switch.
     *
     * @return the application user directory switch
     */
    public UserDirSwitch getUserDirSwitch() {
        return userDirSwitch;
    }

}
