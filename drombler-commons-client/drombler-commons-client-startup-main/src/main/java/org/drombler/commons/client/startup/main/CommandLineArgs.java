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
package org.drombler.commons.client.startup.main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author puce
 */
public class CommandLineArgs {

    /**
     * Switch for specifying the user directory.
     *
     */
    public static final String USER_DIR_SWITCH = "--userdir";
    private final String userDir;
    private final List<String> additionalArguments;

    private CommandLineArgs(String userDir, List<String> additionalArguments) {
        this.userDir = userDir;
        this.additionalArguments = additionalArguments;
    }

    public static CommandLineArgs parseCommandLineArgs(String[] args) {
        List<String> additionalArguments = new ArrayList<>();
        String userDir = null;
        boolean expectUserDir = false;
        for (String arg : args) {
            if (arg.equals(USER_DIR_SWITCH)) {
                expectUserDir = true;
            } else if (expectUserDir) {
                userDir = arg;
                expectUserDir = false;
            } else if (arg.startsWith("-")) {
                wrongArgument(arg);
            } else {
                additionalArguments.add(arg);
            }
        }

        if (expectUserDir && userDir == null) {
            wrongArgument(null);
        }

        return new CommandLineArgs(userDir, additionalArguments);
    }

    private static void wrongArgument(String arg) {
        System.out.println("Usage: [--userdir <user-directory>] [filename 1] .. [filenam n]");
        throw new IllegalArgumentException("Unsupported command line argument: " + arg);
    }

    /**
     * @return the userDir
     */
    public String getUserDir() {
        return userDir;
    }

    /**
     * @return the additionalArguments
     */
    public List<String> getAdditionalArguments() {
        return additionalArguments;
    }

}
