/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
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

    @Override
    public String toString() {
        return "SettingsCategory[" + "id=" + getId() + ", displayName=" + getDisplayName() + ']';
    }
    
    
}
