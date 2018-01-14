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
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.stage;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

/**
 * An application exit request handler.
 * 
 * This is an interface to easily reuse implimentations e.g. in exit menu items and window on close event handlers.
 * 
 * Implementations e.g. can check if the user really wants to close the application or check if there is any unsaved data.
 */
public interface OnExitRequestHandler {

    /**
     * Handles application exit requests.
     *
     * @return true, if the application can exit, false if the request is vetoed.
     */
    boolean handleExitRequest();

    /**
     * Creates a new OnCloseRequest Window-{@link EventHandler} based on the provided OnExitRequestHandler.
     *
     * If {@link #handleExitRequest() } method of the OnExitRequestHandler returns false, the EventHandler will {@link Event#consume()} the {@link WindowEvent}.
     *
     * @param onExitRequestHandler the OnExitRequestHandler
     * @return a new OnCloseRequest {@link EventHandler} based on the provided OnExitRequestHandler
     * @see Window#setOnCloseRequest(javafx.event.EventHandler)
     */
    public static EventHandler<WindowEvent> createOnWindowCloseRequestEventHandler(OnExitRequestHandler onExitRequestHandler) {
        return event -> {
            if (!onExitRequestHandler.handleExitRequest()) {
                event.consume();
            }
        };
    }
}
