package org.drombler.commons.settings.fx;

import java.util.Optional;
import javafx.beans.DefaultProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Control;
import javafx.scene.control.Dialog;
import javafx.scene.control.Skin;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.drombler.commons.settings.SettingsStorageManager;
import org.drombler.commons.settings.fx.impl.skin.SettingsPaneSkin;
import org.drombler.commons.settings.fx.impl.skin.Stylesheets;

/**
 *
 * @author puce
 */
@DefaultProperty("settings")
public class SettingsPane extends Control {

    private static final String DEFAULT_STYLE_CLASS = "settings-pane";
    private ObjectProperty<SettingsCategory> rootSettingsCategory = new SimpleObjectProperty<>(this, "rootSettingsCategory");
    private final ObservableList<SettingsCategory> topCategories = FXCollections.observableArrayList();

    private final ObjectProperty<SettingsStorageManager<?>> settingsStorageManager = new SimpleObjectProperty<>(this, "settingsStorageManager");

    private Dialog<Object> dialog;

    public SettingsPane() {
        getStyleClass().setAll(DEFAULT_STYLE_CLASS);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getUserAgentStylesheet() {
        return Stylesheets.getDefaultStylesheet();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new SettingsPaneSkin(this);
    }

    public ObservableList<SettingsCategory> getTopCategories() {
        return topCategories;
    }

    public final SettingsStorageManager<?> getSettingsStorageManager() {
        return settingsStorageManagerProperty().get();
    }

    public final void setSettingsStorageManager(SettingsStorageManager<?> settingsStorageManager) {
        settingsStorageManagerProperty().set(settingsStorageManager);
    }

    public ObjectProperty<SettingsStorageManager<?>> settingsStorageManagerProperty() {
        return settingsStorageManager;
    }

    public void openDialog(Window owner) {
        if (dialog == null) {
            this.dialog = createDialog();
        }
        dialog.initOwner(owner);
        Optional<Object> fooSettingsOptional = dialog.showAndWait();
        if (fooSettingsOptional.isPresent()) {
//                FooSettings fooSettings = fooSettingsOptional.get();
        }
    }

    private Dialog<Object> createDialog() {
        Dialog<Object> dialog = new Dialog<>();
        dialog.initStyle(StageStyle.UTILITY);
        dialog.getDialogPane().setContent(this);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.APPLY, ButtonType.CANCEL);
        final Node applyNode = dialog.getDialogPane().lookupButton(ButtonType.APPLY);
        if (applyNode instanceof Button) {
            final Button applyButton = (Button) applyNode;
            applyButton.addEventFilter(ActionEvent.ACTION, event -> {
                store();
                event.consume();
            });
        }
        return dialog;
    }

    private void store() {
        // TODO
    }

}
