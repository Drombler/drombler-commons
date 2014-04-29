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
package org.drombler.commons.fx.samples.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import org.drombler.commons.context.ApplicationContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;

/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
/**
 *
 * @author puce
 */
public class SaveAllAction implements EventHandler<ActionEvent>, ApplicationContextSensitive {

    private final MenuItem saveAllMenuItem;
    private Collection<? extends Savable> savables = Collections.emptyList();
    private Context applicationContext;

    public SaveAllAction(MenuItem saveAllMenuItem) {
        this.saveAllMenuItem = saveAllMenuItem;
        saveAllMenuItem.setDisable(true);
    }

    @Override
    public void handle(ActionEvent event) {
        List<Savable> currentSavables = new ArrayList<>(savables); // protect against modification during iteration TODO: needed?
        for (Savable savable : currentSavables) {
            savable.save();
        }
    }

    @Override
    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.applicationContext.addContextListener(Savable.class, new ContextListener() {

            @Override
            public void contextChanged(ContextEvent event) {
                SaveAllAction.this.contextChanged();
            }
        });
        contextChanged();
    }

    private void contextChanged() {
        savables = applicationContext.findAll(Savable.class);
        saveAllMenuItem.setDisable(savables.isEmpty());
    }

}
