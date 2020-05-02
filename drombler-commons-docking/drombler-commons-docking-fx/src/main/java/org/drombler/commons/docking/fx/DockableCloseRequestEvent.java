package org.drombler.commons.docking.fx;

import javafx.event.Event;
import javafx.event.EventTarget;
import javafx.event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Dockable close request event.
 *
 * @author puce
 */
public class DockableCloseRequestEvent extends Event {

    /**
     * The event type.
     */
    public static final EventType<DockableCloseRequestEvent> DOCKABLE_CLOSE_REQUEST = new EventType<>(Event.ANY, "DOCKABLE_CLOSE_REQUEST");

    private static final long serialVersionUID = 3700251117280591632L;
    private static final Logger LOG = LoggerFactory.getLogger(DockableCloseRequestEvent.class);

    private final FXDockableEntry dockableEntry;

    /**
     * Creates a new instance of this class.
     *
     * @param dockableEntry the Dockable entry requested to close
     */
    public DockableCloseRequestEvent(FXDockableEntry dockableEntry) {
        super(DOCKABLE_CLOSE_REQUEST);
        this.dockableEntry = dockableEntry;
    }

    /**
     * Creates a new instance of this class.
     *
     * @param dockableEntry the Dockable entry requested to close
     * @param source the event source
     * @param target the event target
     */
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

    /**
     * {@inheritDoc }
     */
    @Override
    public DockableCloseRequestEvent copyFor(Object newSource, EventTarget newTarget) {
        DockableCloseRequestEvent event = (DockableCloseRequestEvent) super.copyFor(newSource, newTarget);
        LOG.debug("DockableCloseRequestEvent copied for {} and {}", newSource, newTarget);
        return event;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public EventType<? extends DockableCloseRequestEvent> getEventType() {
        return (EventType<? extends DockableCloseRequestEvent>) super.getEventType();
    }

}
