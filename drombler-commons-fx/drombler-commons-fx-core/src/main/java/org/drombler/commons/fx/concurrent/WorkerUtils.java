/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2017 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.concurrent;

import java.util.EnumSet;
import java.util.Set;
import javafx.concurrent.Worker;

/**
 * Utility class for {@link Worker}.
 *  
 * @author puce
 */
public final class WorkerUtils {

    private static final Set<Worker.State> FINISHED_STATES = EnumSet.of(Worker.State.SUCCEEDED, Worker.State.FAILED, Worker.State.CANCELLED);

    private WorkerUtils() {
    }

    /**
     * Gets the finished Worker states: {@link Worker.State#SUCCEEDED}, {@link Worker.State#FAILED} and {@link Worker.State#CANCELLED}.
     *
     * @return the finished Worker states
     */
    // TODO: good name? getTerminalStates() ?
    public static Set<Worker.State> getFinishedStates() {
        return FINISHED_STATES;
    }
}
