package org.drombler.commons.settings.fx.impl.skin;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.settings.fx.SettingsCategory;

/**
 *
 * @author puce
 */
public class SettingsContentPane extends BorderPane{

    @FXML
    private TreeView<SettingsCategory> settingsTreeView;
    
    @FXML
    private BorderPane settingsContentContainer;
    
    public SettingsContentPane() {
        FXMLLoaders.loadRoot(this);
        settingsTreeView.setRoot(new TreeItem<>(new SettingsCategory()));
    }
    
    public TreeItem<SettingsCategory> getRootSettingsCategoryItem(){
        return settingsTreeView.getRoot();
    }
    
}
