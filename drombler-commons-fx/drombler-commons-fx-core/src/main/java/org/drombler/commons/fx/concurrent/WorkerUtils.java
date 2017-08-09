package org.drombler.commons.fx.concurrent;

import java.util.EnumSet;
import java.util.Set;
import javafx.concurrent.Worker;

/**
 *
 * @author puce
 */


public class WorkerUtils {
    private static final Set<Worker.State> FINISHED_STATES = EnumSet.of(Worker.State.SUCCEEDED, Worker.State.FAILED, Worker.State.CANCELLED);

    public static Set<Worker.State> getFinishedStates() {
        return FINISHED_STATES;
    }
}
