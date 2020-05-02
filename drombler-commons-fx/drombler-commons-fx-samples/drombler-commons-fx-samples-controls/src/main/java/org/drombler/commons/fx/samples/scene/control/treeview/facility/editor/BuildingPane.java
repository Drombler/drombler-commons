package org.drombler.commons.fx.samples.scene.control.treeview.facility.editor;

import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Building;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class BuildingPane extends GridPane {

    @FXML
    private TextField nameField;

    @FXML
    private TextField addressField;

    public BuildingPane(Building building) {
        FXMLLoaders.loadRoot(this);

        nameField.textProperty().bindBidirectional(building.nameProperty());
        addressField.textProperty().bindBidirectional(building.addressProperty());
    }

}
