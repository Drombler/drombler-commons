package org.drombler.commons.docking.fx.impl.skin;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import org.drombler.commons.docking.fx.DockableCloseRequestEvent;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.impl.DockingAreaPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author puce
 */
// TODO: is there is an easier way to transform one consumed event to another consumed event?
public class TabManager {

    private static final Logger LOG = LoggerFactory.getLogger(TabManager.class);

    private final EventHandler<DockableCloseRequestEvent> onDockableCloseRequestHandler = new EventHandler<DockableCloseRequestEvent>() {
        @Override
        public void handle(DockableCloseRequestEvent event) {
            final String title = event.getDockableEntry().getDockableData().getTitle();
            LOG.debug("Bubbling event reached window -> not consumed: {}", title);
            if (closeRequest != null) {
                closeRequest.setStopClosingTab(false);
                LOG.debug("Don't stop closing '{}'!", title);

            } else {
                LOG.debug("No active tab close request for: {}", title);
            }
        }
    };
    private CloseRequest closeRequest;

    public EventHandler<DockableCloseRequestEvent> getOnDockableCloseRequestHandler() {
        return onDockableCloseRequestHandler;
    }

    public EventHandler<Event> createOnCloseRequestHandler(Tab tab, FXDockableEntry dockableEntry, DockingAreaPane dockingAreaPane) {
        return event -> {
            final FXDockableData dockableData = dockableEntry.getDockableData();
            LOG.debug("Closing tab: {} ...", dockableData.getTitle());
            closeRequest = new CloseRequest();
            DockableCloseRequestEvent dockableCloseRequestEvent = new DockableCloseRequestEvent(dockableEntry, dockingAreaPane, dockingAreaPane);
            dockingAreaPane.fireEvent(dockableCloseRequestEvent);
            if (closeRequest.isStopClosingTab()) {
                event.consume();
                LOG.debug("onCloseRequest event consumed for: {}", dockableData.getTitle());
            }
            closeRequest = null;
        };
    }

    private class CloseRequest {

        private boolean stopClosingTab = true;

        /**
         * @return the stopClosingTab
         */
        public boolean isStopClosingTab() {
            return stopClosingTab;
        }

        /**
         * @param stopClosingTab the stopClosingTab to set
         */
        public void setStopClosingTab(boolean stopClosingTab) {
            this.stopClosingTab = stopClosingTab;
        }
    }
}
