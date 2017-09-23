package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class StatusBarContentPane extends GridPane {

    @FXML
    private HBox leftPane;

    @FXML
    private HBox centerPane;

    @FXML
    private HBox rightPane;

    public StatusBarContentPane() {
        FXMLLoaders.loadRoot(this);
    }

    /**
     * @return the leftPane
     */
    public Pane getLeftPane() {
        return leftPane;
    }

    /**
     * @return the centerPane
     */
    public Pane getCenterPane() {
        return centerPane;
    }

    /**
     * @return the rightPane
     */
    public Pane getRightPane() {
        return rightPane;
    }

}
