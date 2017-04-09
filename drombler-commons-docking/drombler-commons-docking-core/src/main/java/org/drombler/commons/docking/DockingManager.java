package org.drombler.commons.docking;

import org.drombler.commons.docking.impl.DockingInjector;
import org.drombler.commons.docking.impl.EditorRegistry;
import org.drombler.commons.docking.impl.DockablePreferencesManager;
import org.drombler.commons.docking.impl.DockableDataManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.Injector;
import org.softsmithy.lib.util.ResourceLoader;

/**
 *
 * @author puce
 * @param <D>
 * @param <E>
 * @param <DATA>
 */
public class DockingManager<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(DockingManager.class);

    private final DockableDataManager<D, DATA> dockableDataManager = new DockableDataManager<>();
    private final DockablePreferencesManager<D> dockablePreferencesManager = new DockablePreferencesManager<>();
    private final List<Injector<? super D>> injectors = new ArrayList<>(Arrays.asList(new DockingInjector<>(dockableDataManager)));
    private final EditorRegistry<D, DATA, E> editorRegistry;
    private final Set<E> viewEntries = new HashSet<>();

    private final DockableEntryFactory<D, DATA, E> dockableEntryFactory;
    private final DockableDataFactory<DATA> dockableDataFactory;

    public DockingManager(DockableEntryFactory<D, DATA, E> dockableEntryFactory, DockableDataFactory<DATA> dockableDataFactory, Injector<? super D>... additionalInjectors) {
//        this.dockingAreaContainer = dockingAreaContainer;
        this.dockableEntryFactory = dockableEntryFactory;
        this.dockableDataFactory = dockableDataFactory;

        this.editorRegistry = new EditorRegistry<>();
        this.injectors.addAll(Arrays.asList(additionalInjectors));
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

//    public void registerDockableData(D dockable, String displayName, String icon, ResourceLoader resourceLoader) {
//        DATA dockableData = dockableDataFactory.createDockableData(displayName, icon, resourceLoader);
//        dockableDataManager.registerDockableData(dockable, dockableData);
//    }
//
//    public DATA unregisterDockableData(D dockable) {
//        return dockableDataManager.unregisterDockableData(dockable);
//    }
    public E createEditorEntry(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) throws SecurityException, InvocationTargetException, IllegalAccessException,
            IllegalArgumentException, NoSuchMethodException, InstantiationException {
        D editor = createEditor(content, editorType, icon, resourceLoader);
        return createDockableEntry(editor, DockableKind.EDITOR);
    }

    private E createDockableEntry(D dockable, DockableKind dockableKind) {
        DATA dockableData = dockableDataManager.getDockableData(dockable);
        DockablePreferences dockablePreferences = dockablePreferencesManager.getDockablePreferences(dockable);
        return dockableEntryFactory.createDockableEntry(dockable, dockableKind, dockableData, dockablePreferences);
    }

    private D createEditor(Object content, Class<? extends D> editorType, String icon, ResourceLoader resourceLoader) throws SecurityException, InvocationTargetException, IllegalAccessException,
            IllegalArgumentException, NoSuchMethodException, InstantiationException {
        D editor = createEditor(content, editorType);
        DATA dockableData = dockableDataFactory.createDockableData(icon, resourceLoader);
        dockableDataManager.registerDockableData(editor, dockableData);
        inject(editor);
        return editor;
    }

//    private boolean addDockable(E dockableEntry, boolean active, Context... implicitLocalContexts) {
//        return dockingAreaContainer.addDockable(dockableEntry, active, implicitLocalContexts);
//    }
    private void inject(D dockable) {
        injectors.forEach(injector -> injector.inject(dockable));
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

    public E createAndRegisterViewEntry(Class<? extends D> viewType, String displayName, String icon, ResourceLoader resourceLoader) throws InstantiationException, IllegalAccessException {
        D view = createAndRegisterView(viewType, displayName, icon, resourceLoader);
        E viewEntry = createDockableEntry(view, DockableKind.VIEW);
        viewEntries.add(viewEntry);
        return viewEntry;
    }

    private D createAndRegisterView(Class<? extends D> viewType, String displayName, String icon, ResourceLoader resourceLoader) throws InstantiationException, IllegalAccessException {
        D view = createView(viewType);
        DATA dockableData = dockableDataFactory.createDockableData(displayName, icon, resourceLoader);
        dockableDataManager.registerDockableData(view, dockableData);
        inject(view);
        return view;
    }

    private D createView(Class<? extends D> viewType) throws InstantiationException, IllegalAccessException {
        return viewType.newInstance();
    }

    public void registerEditor(Object uniqueKey, E dockableEntry) {
        if (uniqueKey != null && dockableEntry != null && dockableEntry.getKind() == DockableKind.EDITOR) {
            editorRegistry.registerEditor(uniqueKey, dockableEntry);
        }
    }

    public void unregisterEditor(E editorEntry) throws Exception {
        if (editorEntry != null && editorEntry.getKind() == DockableKind.EDITOR) {
            final D dockable = editorEntry.getDockable();
            dockableDataManager.unregisterDockableData(dockable);
            dockablePreferencesManager.unregisterDockablePreferences(dockable);
            editorRegistry.unregisterEditor(editorEntry);
            closeAutoCloseable(dockable);
        }
    }

    // TODO: call this method
    public void unregisterView(E viewEntry) throws Exception {
        if (viewEntry != null && viewEntry.getKind() == DockableKind.VIEW) {
            final D dockable = viewEntry.getDockable();
            dockableDataManager.unregisterDockableData(dockable);
            dockablePreferencesManager.unregisterDockablePreferences(dockable);
            closeAutoCloseable(dockable);
        }
    }

    private void closeAutoCloseable(D dockable) throws Exception {
        if (dockable instanceof AutoCloseable) {
            ((AutoCloseable) dockable).close();
        }
    }

    @Override
    public void close() {
        closeAllEditors();
        closeAllViews();
    }

    private void closeAllEditors() {
        Set<E> allEditorEntries = editorRegistry.clear();
        allEditorEntries.stream()
                .map(DockableEntry::getDockable)
                .forEach(d -> {
                    try {
                        closeAutoCloseable(d);
                    } catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                    }
                });
    }

    private void closeAllViews() {
        viewEntries.stream()
                .map(DockableEntry::getDockable)
                .forEach(d -> {
                    try {
                        closeAutoCloseable(d);
                    } catch (Exception ex) {
                        LOG.error(ex.getMessage(), ex);
                    }
                });
    }

}
