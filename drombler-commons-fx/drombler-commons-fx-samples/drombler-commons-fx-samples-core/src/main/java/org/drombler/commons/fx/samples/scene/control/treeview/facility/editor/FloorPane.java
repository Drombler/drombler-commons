package org.drombler.commons.fx.samples.scene.control.treeview.facility.editor;

import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Floor;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class FloorPane extends GridPane {

    @FXML
    private TextField nameField;

    public FloorPane(Floor floor) {
        FXMLLoaders.loadRoot(this);

        nameField.textProperty().bindBidirectional(floor.nameProperty());
    }

}
