package org.drombler.commons.docking.fx;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
public class DockableCloseRequestEvent extends Event {

    public static final EventType<DockableCloseRequestEvent> DOCKABLE_CLOSE_REQUEST = new EventType<>(Event.ANY, "DOCKABLE_CLOSE_REQUEST");

    private static final long serialVersionUID = 3700251117280591632L;
    private static final Logger LOG = LoggerFactory.getLogger(DockableCloseRequestEvent.class);

    private final FXDockableEntry dockableEntry;

    public DockableCloseRequestEvent(FXDockableEntry dockableEntry) {
        super(DOCKABLE_CLOSE_REQUEST);
        this.dockableEntry = dockableEntry;
    }

    public DockableCloseRequestEvent(FXDockableEntry dockableEntry, Object source, EventTarget target) {
        super(source, target, DOCKABLE_CLOSE_REQUEST);
        this.dockableEntry = dockableEntry;
    }

    /**
     * @return the dockableEntry
     */
    public FXDockableEntry getDockableEntry() {
        return dockableEntry;
    }

    @Override
    public DockableCloseRequestEvent copyFor(Object newSource, EventTarget newTarget) {
        DockableCloseRequestEvent event = (DockableCloseRequestEvent) super.copyFor(newSource, newTarget);
        LOG.debug("DockableCloseRequestEvent copied for {} and {}", newSource, newTarget);
        return event;
    }

    @Override
    public EventType<? extends DockableCloseRequestEvent> getEventType() {
        return (EventType<? extends DockableCloseRequestEvent>) super.getEventType();
    }

}
