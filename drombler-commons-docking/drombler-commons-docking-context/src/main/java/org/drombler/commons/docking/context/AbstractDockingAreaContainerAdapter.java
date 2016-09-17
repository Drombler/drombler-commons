package org.drombler.commons.docking.context;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.SimpleContext;
import org.drombler.commons.context.SimpleContextContent;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableDataFactory;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.DockableEntryFactory;
import org.drombler.commons.docking.DockablePreferences;
import org.drombler.commons.docking.DockingAreaDescriptor;
import org.drombler.commons.docking.DockingManager;
import org.drombler.commons.docking.context.impl.DockingContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.ResourceLoader;
import org.softsmithy.lib.util.SetChangeEvent;
import org.softsmithy.lib.util.SetChangeListener;
import org.softsmithy.lib.util.UniqueKeyProvider;

public abstract class AbstractDockingAreaContainerAdapter<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements DockingAreaContainer<D, DATA, E>, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDockingAreaContainerAdapter.class);

    private final List<SetChangeListener<DockingAreaDescriptor>> dockingAreaSetChangeListener = new ArrayList<>();
    private final List<SetChangeListener<E>> dockableSetChangeListener = new ArrayList<>();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private final DockableListener dockableListener = new DockableListener();

    private final DockingManager<D, DATA, E> dockingManager;
    private final DockingContextManager<D, DATA, E> dockingContextManager;

    public AbstractDockingAreaContainerAdapter(DockableEntryFactory<D, DATA, E> dockableEntryFactory, DockableDataFactory<DATA> dockableDataFactory) {
        this.dockingManager = new DockingManager<>(dockableEntryFactory, dockableDataFactory);
        this.dockingContextManager = new DockingContextManager<>(this);

        addDockableSetChangeListener(this.dockableListener);
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

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean addDockable(E dockableEntry, boolean active, Context... implicitLocalContexts) {
        boolean added = getDockables().add(dockableEntry);
        addImplicitLocalContext(dockableEntry.getDockable(), implicitLocalContexts);
        if (active) {
            setActiveDockable(dockableEntry);
        }
        return added;
    }

    public void addImplicitLocalContext(D dockable, Context... implicitLocalContexts) {
        dockingContextManager.addImplicitLocalContext(dockable, implicitLocalContexts);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean openView(D dockable, boolean active) {
        E dockableEntry = dockingManager.createDockableViewEntry(dockable);
        return addDockable(dockableEntry, active);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean openAndRegisterNewView(D dockable, boolean active, String displayName, String icon, ResourceLoader resourceLoader) {
        dockingManager.registerDockableData(dockable, displayName, icon, resourceLoader);

        inject(dockable);

        return openView(dockable, active);
    }

    /**
     * {@inheritDoc }
     */
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

    /**
     * {@inheritDoc }
     */
    @Deprecated
    @Override
    public void inject(D dockable) {
        dockingContextManager.inject(dockable);
        dockingManager.inject(dockable);
    }

    /**
     * {@inheritDoc }
     */
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
    public final void addDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener) {
        dockingAreaSetChangeListener.add(listener);
    }

    @Override
    public final void removeDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener) {
        dockingAreaSetChangeListener.remove(listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void addDockableSetChangeListener(SetChangeListener<E> listener) {
        dockableSetChangeListener.add(listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void removeDockableSetChangeListener(SetChangeListener<E> listener) {
        dockableSetChangeListener.remove(listener);
    }

    protected final void fireDockingAreaAdded(Set<DockingAreaDescriptor> sourceSet, DockingAreaDescriptor dockingAreaDescriptor) {
        SetChangeEvent<DockingAreaDescriptor> event = new SetChangeEvent<>(sourceSet, dockingAreaDescriptor);
        dockingAreaSetChangeListener.forEach(listener -> listener.elementAdded(event));
    }

    protected final void fireDockingAreaRemoved(Set<DockingAreaDescriptor> sourceSet, DockingAreaDescriptor dockingAreaDescriptor) {
        SetChangeEvent<DockingAreaDescriptor> event = new SetChangeEvent<>(sourceSet, dockingAreaDescriptor);
        dockingAreaSetChangeListener.forEach(listener -> listener.elementAdded(event));
    }

    protected final void fireDockableAdded(Set<E> sourceSet, E dockableEntry) {
        SetChangeEvent<E> event = new SetChangeEvent<>(sourceSet, dockableEntry);
        dockableSetChangeListener.forEach(listener -> listener.elementAdded(event));
    }

    protected final void fireDockableRemoved(Set<E> sourceSet, E dockableEntry) {
        SetChangeEvent<E> event = new SetChangeEvent<>(sourceSet, dockableEntry);
        dockableSetChangeListener.forEach(listener -> listener.elementRemoved(event));
    }

    protected final void fireActiveDockableChanged(E oldActiveDockableEntry, E newActiveDockableEntry) {
        propertyChangeSupport.firePropertyChange(ACTIVE_DOCKABLE_PROPERTY_NAME, oldActiveDockableEntry, newActiveDockableEntry);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(propertyName, listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        dockingContextManager.close();
    }

    private class DockableListener implements SetChangeListener<E> {

        @Override
        public void elementAdded(SetChangeEvent<E> event) {
            // do nothing
        }

        @Override
        public void elementRemoved(SetChangeEvent<E> event) {
            dockingManager.unregisterEditor(event.getElement());
        }

    }
}
