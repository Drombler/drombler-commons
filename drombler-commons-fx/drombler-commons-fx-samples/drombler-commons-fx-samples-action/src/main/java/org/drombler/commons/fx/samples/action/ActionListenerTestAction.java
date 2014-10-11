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

import org.drombler.commons.action.AbstractActionListener;

/**
 * Implements {@code ActionListener<Object>}.
 *
 * <pre>
 * Pros:
 * + maximally reusable; GUI toolkit agnostic (no dependencies on JavaFX)
 *
 * Cons:
 * - dependencies on Drombler Commons
 * </pre>
 *
 * Note: For JavaFX applications, the generic type parameter passed to ActionListener/ AbstractActionListener must be
 * javafx.event.ActionEvent or one of its super-classes (here: Object).
 *
 * @author puce
 */
public class ActionListenerTestAction extends AbstractActionListener<Object> {

    @Override
    public void onAction(Object event) {
        System.out.println("Test1Action implements ActionListener<Object>!");
    }
}
