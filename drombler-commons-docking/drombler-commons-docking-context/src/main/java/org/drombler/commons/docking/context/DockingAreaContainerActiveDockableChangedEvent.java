package org.drombler.commons.docking.context;

import java.util.EventObject;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public class DockingAreaContainerActiveDockableChangedEvent<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> extends EventObject {

    private static final long serialVersionUID = -2835234753963421562L;

    private final E oldActiveDockable;
    private final E newActiveDockable;

    public DockingAreaContainerActiveDockableChangedEvent(DockingAreaContainer<D, DATA, E> source, E oldActiveDockable, E newActiveDockable) {
        super(source);
        this.oldActiveDockable = oldActiveDockable;
        this.newActiveDockable = newActiveDockable;
    }

    /**
     * @return the oldActiveDockable
     */
    public E getOldActiveDockable() {
        return oldActiveDockable;
    }

    /**
     * @return the newActiveDockable
     */
    public E getNewActiveDockable() {
        return newActiveDockable;
    }

}
