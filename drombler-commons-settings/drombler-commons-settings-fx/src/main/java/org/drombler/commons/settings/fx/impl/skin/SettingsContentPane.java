package org.drombler.commons.settings.fx.impl.skin;

import java.util.IdentityHashMap;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import org.drombler.commons.fx.fxml.FXMLLoaders;
import org.drombler.commons.fx.scene.control.RenderedTreeCellFactory;
import org.drombler.commons.settings.fx.SettingsCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class SettingsContentPane extends BorderPane {

    private static final Logger LOG = LoggerFactory.getLogger(SettingsContentPane.class);

    private final Map<SettingsCategory, Node> contentPanes = new IdentityHashMap<>();

    @FXML
    private TreeView<SettingsCategory> settingsTreeView;

    @FXML
    private BorderPane settingsContentContainer;

    public SettingsContentPane() {
        FXMLLoaders.loadRoot(this);
        settingsTreeView.setRoot(new TreeItem<>(new SettingsCategory()));
        settingsTreeView.setCellFactory(new RenderedTreeCellFactory<>(new SettingsCategoryRenderer()));
        settingsTreeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == null) {
                settingsContentContainer.setCenter(null);
            } else {
                SettingsCategory settingsCategory = newValue.getValue();
                if (!contentPanes.containsKey(settingsCategory)) {
                    registerContentPane(settingsCategory);
                }
                settingsContentContainer.setCenter(contentPanes.get(settingsCategory));
            }
        });
    }

    private void registerContentPane(SettingsCategory settingsCategory) {
        try {
            Node contentPane = settingsCategory.getContentPaneType().newInstance();
            contentPanes.put(settingsCategory, contentPane);
        } catch (InstantiationException | IllegalAccessException ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    public TreeItem<SettingsCategory> getRootSettingsCategoryItem() {
        return settingsTreeView.getRoot();
    }

}
