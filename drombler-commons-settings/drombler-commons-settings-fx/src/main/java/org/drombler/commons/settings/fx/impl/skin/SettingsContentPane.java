package org.drombler.commons.settings.fx.impl.skin;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;

/**
 *
 * @author puce
 */
public class SettingsContentPane extends BorderPane{

    @FXML
    private TreeView<?> settingsTreeView;
    
    @FXML
    private BorderPane settingsContentContainer;
    
    public SettingsContentPane() {
        FXMLLoaders.loadRoot(this);
    }
    
}
