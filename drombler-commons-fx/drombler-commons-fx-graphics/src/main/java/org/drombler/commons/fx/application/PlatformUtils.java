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
package org.drombler.commons.fx.application;

import javafx.application.Platform;

/**
 * Platform Utilities.
 * 
 * @author puce
 */
public final class PlatformUtils {

    private PlatformUtils() {
    }

    /**
     * Runs a {@link Runnable} on the JavaFX Application Thread.
     * <ul>
     *    <li>If the current thread is the JavaFX Application Thread: just execute the {@link Runnable}
     *    <li>Else: Use {@link Platform#runLater(java.lang.Runnable)} to execute the {@link Runnable}
     * </ul>
     * @param runnable a {@link Runnable}
     */
    public static void runOnFxApplicationThread(Runnable runnable) {
        if (Platform.isFxApplicationThread()) {
            runnable.run();
        } else {
            Platform.runLater(runnable);
        }
    }
}
