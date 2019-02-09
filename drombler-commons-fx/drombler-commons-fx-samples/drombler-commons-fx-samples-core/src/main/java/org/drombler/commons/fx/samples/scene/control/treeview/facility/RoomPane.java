package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */


public class RoomPane extends GridPane{
    @FXML
    private TextField nameField;

    @FXML
    private TextField capacityField;
    public RoomPane(Room room) {
                FXMLLoaders.loadRoot(this);

                nameField.textProperty().bindBidirectional(room.nameProperty());
//        capacityField.textProperty().bindBidirectional(room.capacityProperty());
    }
    
}
