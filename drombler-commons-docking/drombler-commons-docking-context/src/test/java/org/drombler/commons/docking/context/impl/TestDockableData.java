package org.drombler.commons.docking.context.impl;

import org.drombler.commons.docking.DockableData;

/**
 *
 * @author puce
 */


public class TestDockableData implements DockableData {

    private String title;
    private boolean modified;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void setModified(boolean modified) {
        this.modified = modified;
    }

}
