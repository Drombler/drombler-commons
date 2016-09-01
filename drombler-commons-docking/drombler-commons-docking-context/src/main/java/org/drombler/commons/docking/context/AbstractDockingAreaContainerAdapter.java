package org.drombler.commons.docking.context;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableDataFactory;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.DockableEntryFactory;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.docking.DockingManager;
import org.drombler.commons.docking.context.impl.DockingContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.ResourceLoader;
import org.softsmithy.lib.util.UniqueKeyProvider;

public abstract class AbstractDockingAreaContainerAdapter<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements DockingAreaContainer<D, DATA, E>, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDockingAreaContainerAdapter.class);

    private final List<DockingAreaContainerListener<D, DATA, E>> listeners = new ArrayList<>();
    private final DockingAreaListener dockingAreaListener = new DockingAreaListener();

    private final DockingManager<D, DATA, E> dockingManager;
    private final DockingContextManager<D, DATA, E> dockingContextManager;

    public AbstractDockingAreaContainerAdapter(DockableEntryFactory<D, DATA, E> dockableEntryFactory, DockableDataFactory<DATA> dockableDataFactory) {
        this.dockingManager = new DockingManager<>(dockableEntryFactory, dockableDataFactory);
        this.dockingContextManager = new DockingContextManager<>(this);

        addDockingAreaContainerListener(this.dockingAreaListener);
    }

//    public DockingManager<D, DATA, E> getDockingManager() {
//        return dockingManager;
//    }
    public Context getActiveContext() {
        return dockingContextManager.getActiveContext();
    }

    public Context getApplicationContext() {
        return dockingContextManager.getApplicationContext();
    }

    public void addImplicitLocalContext(D dockable, Context... implicitLocalContexts) {
        dockingContextManager.addImplicitLocalContext(dockable, implicitLocalContexts);
    }

    @Override
    public boolean openView(D dockable, boolean active) {
        E dockableEntry = dockingManager.createDockableViewEntry(dockable);
        return addDockable(dockableEntry, active);
    }

    @Override
    public boolean openAndRegisterNewView(D dockable, boolean active, String displayName, String icon, ResourceLoader resourceLoader) {
        dockingManager.registerDockableData(dockable, displayName, icon, resourceLoader);

        inject(dockable);

        return openView(dockable, active);
    }

    @Override
    public boolean openEditorForContent(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) {
        boolean opened = true;
        if (content instanceof UniqueKeyProvider) {
            UniqueKeyProvider<?> uniqueKeyProvider = (UniqueKeyProvider<?>) content;
            Object uniqueKey = uniqueKeyProvider.getUniqueKey();
            if (uniqueKey != null) {
                E editorEntry = dockingManager.getRegisteredEditor(uniqueKey);
                if (editorEntry != null) {
                    setActiveDockable(editorEntry);
                } else {
                    opened = openNewEditorForContent(content, editorType, icon, resourceLoader);
                }
            } else {
                opened = openNewEditorForContent(content, editorType, icon, resourceLoader);
            }
        } else {
            opened = openNewEditorForContent(content, editorType, icon, resourceLoader);
        }
        return opened;
    }

    private boolean openNewEditorForContent(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) {
        try {
            D editor = dockingManager.createEditor(content, editorType, icon, resourceLoader);

            inject(editor);

            E editorEntry = dockingManager.createDockableEditorEntry(editor, content);

            SimpleContextContent contextContent = new SimpleContextContent();
            SimpleContext implicitLocalContext = new SimpleContext(contextContent);
            contextContent.add(content);

            if (addDockable(editorEntry, true, implicitLocalContext)) {
                return true;
            } else {
                dockingManager.unregisterEditor(editorEntry);
                return false;
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOG.error(ex.getMessage(), ex);
            return false;
        }
    }

    @Override
    public void inject(D dockable) {
        dockingContextManager.inject(dockable);
        dockingManager.inject(dockable);
    }

    @Override
    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences) {
        dockingManager.registerDefaultDockablePreferences(dockableClass, dockablePreferences);
    }

    @Override
    public DockablePreferences getDockablePreferences(D dockable) {
        return dockingManager.getDockablePreferences(dockable);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void addDockingAreaContainerListener(DockingAreaContainerListener<D, DATA, E> listener) {
        listeners.add(listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void removeDockingAreaContainerListener(DockingAreaContainerListener<D, DATA, E> listener) {
        listeners.remove(listener);
    }

    protected final void fireDockingAreaAdded(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<D, DATA, E> event = new DockingAreaContainerDockingAreaEvent<>(this, dockingAreaId);
        listeners.forEach(listener -> listener.dockingAreaAdded(event));
    }

    protected final void fireDockingAreaRemoved(String dockingAreaId) {
        DockingAreaContainerDockingAreaEvent<D, DATA, E> event = new DockingAreaContainerDockingAreaEvent<>(this, dockingAreaId);
        listeners.forEach(listener -> listener.dockingAreaRemoved(event));
    }

    protected final void fireDockableAdded(E dockableEntry) {
        DockingAreaContainerDockableEvent<D, DATA, E> event = new DockingAreaContainerDockableEvent<>(this, dockableEntry);
        listeners.forEach(listener -> listener.dockableAdded(event));
    }

    protected final void fireDockableRemoved(E dockableEntry) {
        DockingAreaContainerDockableEvent<D, DATA, E> event = new DockingAreaContainerDockableEvent<>(this, dockableEntry);
        listeners.forEach(listener -> listener.dockableRemoved(event));
    }

    protected final void fireActiveDockableChanged(E oldActiveDockableEntry, E newActiveDockableEntry) {
        DockingAreaContainerActiveDockableChangedEvent<D, DATA, E> event = new DockingAreaContainerActiveDockableChangedEvent<>(this, oldActiveDockableEntry, newActiveDockableEntry);
        listeners.forEach(listener -> listener.activeDockableChanged(event));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        dockingContextManager.close();
    }

    private class DockingAreaListener implements DockingAreaContainerListener<D, DATA, E> {

        /**
         * This method gets called from the application thread!
         *
         * @param event
         */
        @Override
        public void dockingAreaAdded(DockingAreaContainerDockingAreaEvent<D, DATA, E> event) {
            // do nothing
        }

        /**
         * This method gets called from the application thread!
         *
         * @param event
         */
        @Override
        public void dockingAreaRemoved(DockingAreaContainerDockingAreaEvent<D, DATA, E> event) {
            // do nothing
        }

        @Override
        public void dockableAdded(DockingAreaContainerDockableEvent<D, DATA, E> event) {
            // do nothing
        }

        @Override
        public void dockableRemoved(DockingAreaContainerDockableEvent<D, DATA, E> event) {
            dockingManager.unregisterEditor(event.getDockableEntry());
        }

        @Override
        public void activeDockableChanged(DockingAreaContainerActiveDockableChangedEvent<D, DATA, E> event) {
            // do nothing
        }

    }
}
