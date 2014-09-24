package org.drombler.commons.fx.samples.action;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 *
 * @author puce
 */
public class ExitActionEventHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent t) {
        Platform.exit();
    }
}
