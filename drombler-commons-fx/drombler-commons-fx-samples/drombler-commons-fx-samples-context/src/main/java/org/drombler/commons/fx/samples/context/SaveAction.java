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
package org.drombler.commons.fx.samples.context;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;

/**
 *
 * @author puce
 */
public class SaveAction implements EventHandler<ActionEvent>, ActiveContextSensitive {

    private final MenuItem saveMenuItem;
    private Savable savable;
    private Context activeContext;

    public SaveAction(MenuItem saveMenuItem) {
        this.saveMenuItem = saveMenuItem;
        saveMenuItem.setDisable(true);
    }

    @Override
    public void handle(ActionEvent event) {
        savable.save();
    }

    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.activeContext.addContextListener(Savable.class, new ContextListener() {

            @Override
            public void contextChanged(ContextEvent event) {
                SaveAction.this.contextChanged();
            }
        });
        contextChanged();
    }

    private void contextChanged() {
        savable = activeContext.find(Savable.class);
        saveMenuItem.setDisable(savable == null);
    }

}
