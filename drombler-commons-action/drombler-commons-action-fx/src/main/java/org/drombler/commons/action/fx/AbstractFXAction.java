/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.action.fx;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanPropertyBase;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.input.KeyCombination;
import org.drombler.commons.client.graphic.GraphicFactory;

/**
 * A base class for {@link FXAction}s.
 *
 * @author puce
 */
public abstract class AbstractFXAction implements FXAction {

    /**
     * {@inheritDoc }
     */
    private final StringProperty displayName = new SimpleStringProperty(this, "displayName", null);
    /**
     * {@inheritDoc }
     */
    private final ObjectProperty<KeyCombination> accelerator = new SimpleObjectProperty<>(this, "accelerator", null);
    /**
     * {@inheritDoc }
     */
    private final EnabledProperty enabled = new EnabledProperty();
    /**
     * {@inheritDoc }
     */
    private final ObjectProperty<GraphicFactory<Node>> graphicFactory = new SimpleObjectProperty<>(this, "graphicFactory",
            null);

    @Override
    public final String getDisplayName() {
        return displayNameProperty().get();
    }

    @Override
    public final void setDisplayName(String displayName) {
        displayNameProperty().set(displayName);
    }

    @Override
    public StringProperty displayNameProperty() {
        return displayName;
    }

    @Override
    public final KeyCombination getAccelerator() {
        return acceleratorProperty().get();
    }

    @Override
    public final void setAccelerator(KeyCombination keyCombination) {
        acceleratorProperty().set(keyCombination);
    }

    @Override
    public ObjectProperty<KeyCombination> acceleratorProperty() {
        return accelerator;
    }

    @Override
    public final boolean isEnabled() {
        return enabledProperty().get();
    }

    protected void setEnabled(boolean enabled) {
        this.enabled.set(enabled);
    }

    @Override
    public ReadOnlyBooleanProperty enabledProperty() {
        return enabled;
    }

    @Override
    public final GraphicFactory<Node> getGraphicFactory() {
        return graphicFactoryProperty().get();
    }

    @Override
    public final void setGraphicFactory(GraphicFactory<Node> graphicFactory) {
        graphicFactoryProperty().set(graphicFactory);
    }

    @Override
    public ObjectProperty<GraphicFactory<Node>> graphicFactoryProperty() {
        return graphicFactory;
    }

    private class EnabledProperty extends ReadOnlyBooleanPropertyBase {

        private boolean enabled = true;

        @Override
        public final boolean get() {
            return enabled;
        }

        private void set(boolean newValue) {
            if (enabled != newValue) {
                enabled = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return AbstractFXAction.this;
        }

        @Override
        public String getName() {
            return "enabled";
        }
    }
}
