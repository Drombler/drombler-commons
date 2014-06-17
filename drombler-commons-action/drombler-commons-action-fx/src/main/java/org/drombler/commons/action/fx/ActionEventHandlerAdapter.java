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
package org.drombler.commons.action.fx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * A {@link FXAction} adapter for {@code EventHandler<ActionEvent>}.
 *
 * This class allows to use a {@code EventHandler<ActionEvent>} as a FXAction.
 *
 * @author puce
 */
public class ActionEventHandlerAdapter extends AbstractFXActionAdapter<EventHandler<ActionEvent>> {

    /**
     * Creates a new instance of this class.
     *
     * @param actionEventHandler the action event handler
     */
    public ActionEventHandlerAdapter(EventHandler<ActionEvent> actionEventHandler) {
        super(actionEventHandler);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void handle(ActionEvent e) {
        getAdapted().handle(e);
    }
}
