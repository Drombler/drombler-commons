package org.drombler.commons.docking.context.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import org.drombler.commons.docking.DockableData;

/**
 *
 * @author puce
 */
public class TestDockableData implements DockableData {

    private String title;
    private boolean modified;
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        String oldTitle = this.title;
        this.title = title;
        propertyChangeSupport.firePropertyChange(TITLE_PROPERTY_NAME, oldTitle, this.title);
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        boolean oldModified = this.modified;
        this.modified = modified;
        propertyChangeSupport.firePropertyChange(MODIFIED_PROPERTY_NAME, oldModified, this.modified);
    }

    @Override
    public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    @Override
    public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

}
