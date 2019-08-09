package org.drombler.commons.docking.context;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableDataFactory;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.DockableEntryFactory;
import org.drombler.commons.docking.DockableKind;
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

/**
 * An abstract base class for {@link DockingAreaContainer} implementations.
 *
 * @param <D> the Dockable type
 * @param <DATA> the Dockable data type
 * @param <E> the Dockable entry type
 */
public abstract class AbstractDockingAreaContainer<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements DockingAreaContainer<D, DATA, E>, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDockingAreaContainer.class);

    private final List<SetChangeListener<DockingAreaDescriptor>> dockingAreaSetChangeListeners = new ArrayList<>();
    private final List<SetChangeListener<E>> dockableSetChangeListeners = new ArrayList<>();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private final DockableListener dockableListener = new DockableListener();

    private final DockingManager<D, DATA, E> dockingManager;
    private final DockingContextManager<D, DATA, E> dockingContextManager;

    /**
     * Creates a new instance of this class.
     *
     * @param dockableEntryFactory the Dockable entry factory
     * @param dockableDataFactory the Dockable data factory
     * @param contextManager the context manager
     */
    public AbstractDockingAreaContainer(DockableEntryFactory<D, DATA, E> dockableEntryFactory, DockableDataFactory<DATA> dockableDataFactory, ContextManager contextManager) {
        this.dockingContextManager = new DockingContextManager<>(this, contextManager);
        this.dockingManager = new DockingManager<>(dockableEntryFactory, dockableDataFactory, dockingContextManager.getContextInjector());

        addDockableSetChangeListener(this.dockableListener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean addDockable(E dockableEntry, boolean active, Context... implicitLocalContexts) {
        boolean added = getDockables().add(dockableEntry);
        dockingContextManager.addImplicitLocalContext(dockableEntry.getDockable(), implicitLocalContexts);
        if (active) {
            setActiveDockable(dockableEntry);
        }
        return added;
    }

    /**
     * Gets the content of the specified type from the local context of the specified Dockable.
     *
     * @param <T> the content type
     * @param dockable the Dockable
     * @param contentType the content type
     * @return the content
     */
    // TODO: does this method belong to this class?
    public <T> T getContent(D dockable, Class<T> contentType) {
        Context localContext = dockingContextManager.getLocalContext(dockable);
        return localContext.find(contentType);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public E openAndRegisterNewView(Class<? extends D> viewType, boolean active, String displayName, String icon, ResourceLoader resourceLoader) {
        try {
            E viewEntry = dockingManager.createAndRegisterViewEntry(viewType, displayName, icon, resourceLoader);
            if (addDockable(viewEntry, active)) {
                return viewEntry;
            } else {
                return null;
            }
        } catch (InstantiationException | IllegalAccessException | RuntimeException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void closeAndUnregisterView(E viewEntry) {
        try {
            if (viewEntry != null && viewEntry.getKind() == DockableKind.VIEW) {
                getDockables().remove(viewEntry);
                dockingManager.unregisterView(viewEntry);
            }
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public E openEditorForContent(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) {
        if (content instanceof UniqueKeyProvider) {
            return openEditorForUniqueKeyProvider((UniqueKeyProvider<?>) content, editorType, icon, resourceLoader);
        } else {
            return openNewEditorForContent(content, editorType, icon, resourceLoader);
        }
    }

    private E openEditorForUniqueKeyProvider(UniqueKeyProvider<?> uniqueKeyProvider, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) {
        final E editorEntry;
        Object uniqueKey = uniqueKeyProvider.getUniqueKey();
        if (uniqueKey != null) {
            if (dockingManager.containsRegisteredEditor(uniqueKey)) {
                editorEntry = dockingManager.getRegisteredEditor(uniqueKey);
                setActiveDockable(editorEntry);
            } else {
                editorEntry = openNewEditorForContent(uniqueKeyProvider, editorType, icon, resourceLoader);
                if (editorEntry != null) {
                    dockingManager.registerEditor(uniqueKey, editorEntry);
                }
            }
        } else {
            editorEntry = openNewEditorForContent(uniqueKeyProvider, editorType, icon, resourceLoader);
            if (editorEntry != null) {
                final PropertyChangeListener savedListener = new PropertyChangeListener() {
                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        boolean saved = !((Boolean) evt.getNewValue());
                        if (saved && uniqueKeyProvider.getUniqueKey() != null && !dockingManager.containsRegisteredEditor(uniqueKey)) {
                            dockingManager.registerEditor(uniqueKeyProvider.getUniqueKey(), editorEntry);
                            editorEntry.getDockableData().removePropertyChangeListener(DockableData.MODIFIED_PROPERTY_NAME, this);
                        }
                    }
                };
                editorEntry.getDockableData().addPropertyChangeListener(DockableData.MODIFIED_PROPERTY_NAME, savedListener);
            }
        }
        return editorEntry;
    }

    private E openNewEditorForContent(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) {
        try {
            E editorEntry = dockingManager.createEditorEntry(content, editorType, icon, resourceLoader);

            Context implicitLocalContext = Contexts.createFixedContext(Arrays.asList(content));

            if (addDockable(editorEntry, true, implicitLocalContext)) {
                return editorEntry;
            } else {
                return null;
            }
        } catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOG.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void closeEditors(Class<? extends D> editorType) {
        getDockables().removeIf(dockableEntry -> (dockableEntry.getKind() == DockableKind.EDITOR) && dockableEntry.getDockable().getClass().equals(editorType));
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences) {
        dockingManager.registerDefaultDockablePreferences(dockableClass, dockablePreferences);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DockablePreferences unregisterDefaultDockablePreferences(Class<?> dockableClass) {
        return dockingManager.unregisterDefaultDockablePreferences(dockableClass);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public DockablePreferences getDockablePreferences(D dockable) {
        return dockingManager.getDockablePreferences(dockable);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void addDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener) {
        dockingAreaSetChangeListeners.add(listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void removeDockingAreaSetChangeListener(SetChangeListener<DockingAreaDescriptor> listener) {
        dockingAreaSetChangeListeners.remove(listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void addDockableSetChangeListener(SetChangeListener<E> listener) {
        dockableSetChangeListeners.add(listener);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void removeDockableSetChangeListener(SetChangeListener<E> listener) {
        dockableSetChangeListeners.remove(listener);
    }

    /**
     * Fires an elementAdded event to the Docking Area {@link SetChangeListener}s.
     *
     * @param sourceSet the source set
     * @param dockingAreaDescriptor the Docking Area descriptor which was added to the set
     */
    protected final void fireDockingAreaAdded(Set<DockingAreaDescriptor> sourceSet, DockingAreaDescriptor dockingAreaDescriptor) {
        SetChangeEvent<DockingAreaDescriptor> event = new SetChangeEvent<>(sourceSet, dockingAreaDescriptor);
        dockingAreaSetChangeListeners.forEach(listener -> listener.elementAdded(event));
    }

    /**
     * Fires an elementRemoved event to the Docking Area {@link SetChangeListener}s.
     *
     * @param sourceSet the source set
     * @param dockingAreaDescriptor the Docking Area descriptor which was removed from the set
     */
    protected final void fireDockingAreaRemoved(Set<DockingAreaDescriptor> sourceSet, DockingAreaDescriptor dockingAreaDescriptor) {
        SetChangeEvent<DockingAreaDescriptor> event = new SetChangeEvent<>(sourceSet, dockingAreaDescriptor);
        dockingAreaSetChangeListeners.forEach(listener -> listener.elementRemoved(event));
    }

    /**
     * Fires an elementAdded event to the Dockable {@link SetChangeListener}s.
     *
     * @param sourceSet the source set
     * @param dockableEntry the Dockable entry which was added to the set
     */
    protected final void fireDockableAdded(Set<E> sourceSet, E dockableEntry) {
        SetChangeEvent<E> event = new SetChangeEvent<>(sourceSet, dockableEntry);
        dockableSetChangeListeners.forEach(listener -> listener.elementAdded(event));
    }

    /**
     * Fires an elementRemoved event to the Dockable {@link SetChangeListener}s.
     *
     * @param sourceSet the source set
     * @param dockableEntry the Dockable entry which was removed from the set
     */
    protected final void fireDockableRemoved(Set<E> sourceSet, E dockableEntry) {
        SetChangeEvent<E> event = new SetChangeEvent<>(sourceSet, dockableEntry);
        dockableSetChangeListeners.forEach(listener -> listener.elementRemoved(event));
    }

    /**
     * Fires a property change event for the 'activeDockable' property.
     *
     * @param oldActiveDockableEntry the previously active Dockable entry if any, else null
     * @param newActiveDockableEntry the new active Dockable entry if any, else null
     */
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
        dockingManager.close();
    }

    private class DockableListener implements SetChangeListener<E> {

        @Override
        public void elementAdded(SetChangeEvent<E> event) {
            // do nothing
        }

        @Override
        public void elementRemoved(SetChangeEvent<E> event) {
            try {
                dockingManager.unregisterEditor(event.getElement());
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

    }
}
