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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.samples.action;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Implements {@code javafx.event.EventHandler<javafx.event.ActionEvent>}.
 *
 * <pre>
 * Pros:
 * + very close to standard JavaFX code
 * + no dependencies on Drombler Commons
 *
 * Cons:
 * - dependencies on JavaFX
 * </pre>
 *
 * @author puce
 */
public class EventHandlerTestAction implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        System.out.println("Test2Action implements EventHandler<ActionEvent>!");
    }
}
