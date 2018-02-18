package org.drombler.commons.settings.fx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class SettingsCategory {

    private final StringProperty id = new SimpleStringProperty(this, "id");
    private final StringProperty displayName = new SimpleStringProperty(this, "displayName");
    private final StringProperty displayDescription = new SimpleStringProperty(this, "displayDescription");
    private final ObjectProperty<Class<? extends Node>> contentPaneType = new SimpleObjectProperty<>(this, "contentPaneType");
    private final ObservableList<SettingsCategory> subCategories = FXCollections.observableArrayList();

    public final String getId() {
        return idProperty().get();
    }

    public final void setId(String id) {
        idProperty().set(id);
    }

    public StringProperty idProperty() {
        return id;
    }

    public final String getDisplayName() {
        return displayNameProperty().get();
    }

    public final void setDisplayName(String displayName) {
        displayNameProperty().set(displayName);
    }

    public StringProperty displayNameProperty() {
        return displayName;
    }

    public final String getDisplayDescription() {
        return displayDescriptionProperty().get();
    }

    public final void setDisplayDescription(String displayDescription) {
        displayDescriptionProperty().set(displayDescription);
    }

    public StringProperty displayDescriptionProperty() {
        return displayDescription;
    }

    public final Class<? extends Node> getContentPaneType() {
        return contentPaneTypeProperty().get();
    }

    public final void setContentPaneType(Class<? extends Node> contentPaneType) {
        contentPaneTypeProperty().set(contentPaneType);
    }

    public ObjectProperty<Class<? extends Node>> contentPaneTypeProperty() {
        return contentPaneType;
    }
    
    public ObservableList<SettingsCategory> getSubCategories(){
        return subCategories;
    }
}
