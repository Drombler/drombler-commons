package org.drombler.commons.docking.context;

import java.beans.PropertyChangeEvent;
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

public abstract class AbstractDockingAreaContainer<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements DockingAreaContainer<D, DATA, E>, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractDockingAreaContainer.class);

    private final List<SetChangeListener<DockingAreaDescriptor>> dockingAreaSetChangeListener = new ArrayList<>();
    private final List<SetChangeListener<E>> dockableSetChangeListener = new ArrayList<>();
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private final DockableListener dockableListener = new DockableListener();

    private final DockingManager<D, DATA, E> dockingManager;
    private final DockingContextManager<D, DATA, E> dockingContextManager;

    public AbstractDockingAreaContainer(DockableEntryFactory<D, DATA, E> dockableEntryFactory, DockableDataFactory<DATA> dockableDataFactory) {
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


    // TODO: does this method belong to this class?
    public <T> T getContent(D dockable, Class<T> contentType) {
        Context localContext = dockingContextManager.getLocalContext(dockable);
        return localContext.find(contentType);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean openView(D dockable, boolean active) {
        E dockableEntry = dockingManager.createDockableEntry(dockable, DockableKind.VIEW);
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
    public void closeAndUnregisterView(D dockable) {
        E dockableEntry = dockingManager.createDockableEntry(dockable, DockableKind.VIEW);
        getDockables().remove(dockableEntry);
        dockingManager.unregisterDockableData(dockable);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean openEditorForContent(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) {
        final E editorEntry;
        if (content instanceof UniqueKeyProvider) {
            editorEntry = openEditorForUniqueKeyProvider((UniqueKeyProvider<?>) content, editorType, icon, resourceLoader);
        } else {
            editorEntry = openNewEditorForContent(content, editorType, icon, resourceLoader);
        }
        return editorEntry != null;
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
            D editor = dockingManager.createEditor(content, editorType, icon, resourceLoader);

            inject(editor);

            E editorEntry = dockingManager.createDockableEntry(editor, DockableKind.EDITOR);

            Context implicitLocalContext = createImplicitLocalContext(content);

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

    @Override
    public void closeEditors(Class<? extends D> editorType) {
        getDockables().removeIf(dockableEntry -> (dockableEntry.getKind() == DockableKind.EDITOR) && dockableEntry.getDockable().getClass().equals(editorType));
    }

    private Context createImplicitLocalContext(Object content) {
        SimpleContextContent contextContent = new SimpleContextContent();
        Context implicitLocalContext = new SimpleContext(contextContent);
        contextContent.add(content);
        return implicitLocalContext;
    }

    private void inject(D dockable) {
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

    /**
     * {@inheritDoc }
     */
    @Override
    public DockablePreferences unregisterDefaultDockablePreferences(Class<?> dockableClass) {
        return dockingManager.unregisterDefaultDockablePreferences(dockableClass);
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
            try {
                dockingManager.unregisterEditor(event.getElement());
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }

    }
}
