package org.drombler.commons.fx.samples.data;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;

/**
 *
 * @author puce
 */
public abstract class AbstractFacilityTreeItem<T extends Facility> extends TreeItem<Facility>{

    private final T facility;

    /**
     *
     * @param facility
     */
    public AbstractFacilityTreeItem(T facility) {
        super(facility);
        this.facility = facility;
    }
    
    public abstract Node createFacilityEditor();

    /**
     * @return the facility
     */
    public T getFacility() {
        return facility;
    }
}
