package org.drombler.commons.fx.samples.data;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */


public class BuildingPane extends GridPane{

    @FXML
    private TextField addressField;
    
    public BuildingPane(Building building) {
        FXMLLoaders.loadRoot(this);
        
        addressField.textProperty().bindBidirectional(building.addressProperty());
    }
    
}
