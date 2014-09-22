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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.drombler.commons.action.AbstractActionListener;
import org.drombler.commons.context.ApplicationContextSensitive;
import org.drombler.commons.context.Context;

/**
 *
 * @author puce
 */
public class ApplicationContextSensitiveAction extends AbstractActionListener<Object> implements ApplicationContextSensitive {

    private Collection<? extends MyCommand> myCommands = Collections.emptyList();
    private Context applicationContext;

    public ApplicationContextSensitiveAction() {
        setEnabled(false);
    }

    @Override
    public void onAction(Object event) {
        List<MyCommand> currentMyCommands = new ArrayList<>(myCommands); // protect against modification during iteration TODO: needed?
        currentMyCommands.forEach(myCommand -> myCommand.doSomething());
    }

    @Override
    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.applicationContext.addContextListener(MyCommand.class, event -> contextChanged());
        contextChanged();
    }

    private void contextChanged() {
        myCommands = applicationContext.findAll(MyCommand.class);
        setEnabled(!myCommands.isEmpty());
    }
}
