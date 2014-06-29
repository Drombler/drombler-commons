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
package org.drombler.commons.fx.samples.docking;

import java.io.IOException;
import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import org.drombler.commons.fx.scene.SimpleEventHandlerProperty;
import org.drombler.commons.fx.docking.DockablePane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

public class LeftTestPane extends DockablePane {

    private final SimpleEventHandlerProperty<ActionEvent> onNewSampleAction = new SimpleEventHandlerProperty<>(this,
            "onNewSampleAction", ActionEvent.ACTION);

    public LeftTestPane() throws IOException {
        load();
        onNewSampleAction.setEventHandlerRegistrar(() -> setEventHandler(onNewSampleAction.getEventType(),
                onNewSampleAction.get()));
    }

    private void load() throws IOException {
        FXMLLoaders.loadRoot(this);
    }

    @FXML
    private void onNewSampleAction(ActionEvent event) {
        event.consume();
        fireEvent(new ActionEvent());
    }

    public final EventHandler<ActionEvent> getOnNewSampleAction() {
        return onNewSampleActionProperty().get();
    }

    public final void setOnNewSampleAction(EventHandler<ActionEvent> contentChanged) {
        onNewSampleActionProperty().set(contentChanged);
    }

    public ObjectProperty<EventHandler<ActionEvent>> onNewSampleActionProperty() {
        return onNewSampleAction;
    }

}
