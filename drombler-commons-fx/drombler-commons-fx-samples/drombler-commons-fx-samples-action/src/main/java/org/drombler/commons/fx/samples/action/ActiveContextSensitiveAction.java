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
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;

/**
 * The following sample shows a active context sensitive action implementation. It looks for a MyCommand instance in the
 * active context and listens for changes of the active context. If it finds a MyCommand instance, the Action gets
 * enabled, else it gets disabled. If the Action gets triggered (onAction-method), then a method of MyCommand gets
 * called.
 *
 * @author puce
 */
public class ActiveContextSensitiveAction extends AbstractActionListener<Object> implements ActiveContextSensitive {

    private MyCommand myCommand;
    private Context activeContext;

    public ActiveContextSensitiveAction() {
        setEnabled(false);
    }

    @Override
    public void onAction(Object event) {
        myCommand.doSomething();
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(MyCommand.class, event -> contextChanged());
        contextChanged();
    }

    private void contextChanged() {
        myCommand = activeContext.find(MyCommand.class);
        setEnabled(myCommand != null);
    }

}
