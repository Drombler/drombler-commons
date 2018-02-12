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
package org.drombler.commons.fx.concurrent;

import java.util.concurrent.Executor;
import org.drombler.commons.fx.application.PlatformUtils;


/**
 * An {@link Executor}, which executes the provided {@link Runnable} on the JavaFX Application Thread.
 * 
 * @see PlatformUtils
 * @author puce
 */
public class FXApplicationThreadExecutor implements Executor {

    @Override
    public void execute(Runnable command) {
        PlatformUtils.runOnFxApplicationThread(command);
    }
}
