package org.drombler.commons.docking;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.softsmithy.lib.util.ResourceLoader;

/**
 *
 * @author puce
 * @param <D>
 * @param <E>
 * @param <DATA>
 */
public class DockingManager<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> {

    private final DockableDataManager<D, DATA> dockableDataManager = new SimpleDockableDataManager<>();
    private final DockablePreferencesManager<D> dockablePreferencesManager = new SimpleDockablePreferencesManager<>();
    private final DockingInjector<D, DATA> dockingInjector = new DockingInjector<>(dockableDataManager);
    private final EditorRegistry<D, DATA, E> editorRegistry;

    private final DockableEntryFactory<D, DATA, E> dockableEntryFactory;
    private final DockableDataFactory<DATA> dockableDataFactory;

    public DockingManager(DockableEntryFactory<D, DATA, E> dockableEntryFactory, DockableDataFactory<DATA> dockableDataFactory) {
//        this.dockingAreaContainer = dockingAreaContainer;
        this.dockableEntryFactory = dockableEntryFactory;
        this.dockableDataFactory = dockableDataFactory;

        this.editorRegistry = new SimpleEditorRegistry<>();
    }

//    public boolean openView(D dockable) {
//        return openView(dockable, true);
//    }
    public boolean containsRegisteredEditor(Object uniqueKey) {
        return editorRegistry.containsEditor(uniqueKey);
    }

    public E getRegisteredEditor(Object uniqueKey) {
        return editorRegistry.getEditor(uniqueKey);
    }

    public void registerDockableData(D dockable, String displayName, String icon, ResourceLoader resourceLoader) {
        DATA dockableData = dockableDataFactory.createDockableData(displayName, icon, resourceLoader);
        dockableDataManager.registerDockableData(dockable, dockableData);
    }

    public DATA unregisterDockableData(D dockable) {
        return dockableDataManager.unregisterDockableData(dockable);
    }

    public E createDockableEntry(D dockable, DockableKind dockableKind) {
        DATA dockableData = dockableDataManager.getDockableData(dockable);
        DockablePreferences dockablePreferences = dockablePreferencesManager.getDockablePreferences(dockable);
        return dockableEntryFactory.createDockableEntry(dockable, dockableKind, dockableData, dockablePreferences);
    }

    public D createEditor(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) throws SecurityException, InvocationTargetException, IllegalAccessException,
            IllegalArgumentException, NoSuchMethodException, InstantiationException {
        D editor = createEditor(content, editorType);
        DATA dockableData = dockableDataFactory.createDockableData(icon, resourceLoader);
        dockableDataManager.registerDockableData(editor, dockableData);
        return editor;
    }

//    private boolean addDockable(E dockableEntry, boolean active, Context... implicitLocalContexts) {
//        return dockingAreaContainer.addDockable(dockableEntry, active, implicitLocalContexts);
//    }
    public void inject(D dockable) {
        dockingInjector.inject(dockable);
    }

    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences) {
        dockablePreferencesManager.registerDefaultDockablePreferences(dockableClass, dockablePreferences);
    }

    public DockablePreferences unregisterDefaultDockablePreferences(Class<?> dockableClass) {
        return dockablePreferencesManager.unregisterDefaultDockablePreferences(dockableClass);
    }

    public DockablePreferences getDockablePreferences(D dockable) {
        return dockablePreferencesManager.getDockablePreferences(dockable);
    }

    private D createEditor(Object content, Class<? extends D> editorType) throws IllegalAccessException, SecurityException, InvocationTargetException, InstantiationException, IllegalArgumentException,
            NoSuchMethodException {
        Constructor<? extends D> editorConstructor = editorType.getConstructor(content.getClass());
        return editorConstructor.newInstance(content);
    }

    public void registerEditor(Object uniqueKey, E dockableEntry) {
        if (uniqueKey != null && dockableEntry != null && dockableEntry.getKind() == DockableKind.EDITOR) {
            editorRegistry.registerEditor(uniqueKey, dockableEntry);
        }
    }

    public void unregisterEditor(E dockableEntry) throws Exception {
        if (dockableEntry != null && dockableEntry.getKind() == DockableKind.EDITOR) {
            final D dockable = dockableEntry.getDockable();
            dockableDataManager.unregisterDockableData(dockable);
            dockablePreferencesManager.unregisterDockablePreferences(dockable);
            editorRegistry.unregisterEditor(dockableEntry);
            if (dockable instanceof AutoCloseable) {
                ((AutoCloseable) dockable).close();
            }
        }
    }

}
