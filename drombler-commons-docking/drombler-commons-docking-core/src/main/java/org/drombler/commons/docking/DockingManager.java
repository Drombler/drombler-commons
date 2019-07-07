package org.drombler.commons.docking;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.drombler.commons.docking.impl.DockableDataManager;
import org.drombler.commons.docking.impl.DockablePreferencesManager;
import org.drombler.commons.docking.impl.DockingInjector;
import org.drombler.commons.docking.impl.EditorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.Injector;
import org.softsmithy.lib.util.ResourceLoader;

/**
 * The docking manager manages Dockable views and editors along with their associated Dockable data and Dockable preferences.
 *
 * @author puce
 * @param <D> the Dockable type
 * @param <DATA> the Dockable data type
 * @param <E> the Dockable entry type
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

    /**
     * Creates a new instance of this class.
     *
     * @param dockableEntryFactory the Dockable entry factory
     * @param dockableDataFactory the Dockable data factory
     * @param additionalInjectors additional injectors to be applied to Dockables
     */
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
    /**
     * Checks if their is a registered editor Dockable for the provided unique key of some content. A unique key can e.g. be a file path or a business key of a business object.
     *
     * @param uniqueKey the unique key of some content
     * @return true if this manager contains a registered editor for the provided unique key, else false
     */
    public boolean containsRegisteredEditor(Object uniqueKey) {
        return editorRegistry.containsEditor(uniqueKey);
    }

    /**
     * Gets the registered editor Dockable for the provided unique key of some content. A unique key can e.g. be a file path or a business key of a business object.
     *
     * @param uniqueKey the unique key of some content
     * @return the registered editor Dockable for the provided unique key of some content, if there is one, else null
     */
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
    /**
     * Creates an editor {@link DockableEntry}.
     *
     * @param content the content to create the editor for
     * @param editorType the editor type
     * @param icon the icon name pattern
     * @param resourceLoader the resource loader to load the icon
     * @return a new editor Dockable entry
     * @throws SecurityException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @see DockableKind#EDITOR
     */
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

    /**
     * Registers the default {@link DockablePreferences} for the provided Dockable class.
     *
     * @param dockableClass the Dockable class
     * @param dockablePreferences the default Dockable preferences
     */
    public void registerDefaultDockablePreferences(Class<?> dockableClass, DockablePreferences dockablePreferences) {
        dockablePreferencesManager.registerDefaultDockablePreferences(dockableClass, dockablePreferences);
    }

    /**
     * Unegisters the default {@link DockablePreferences} for the provided Dockable class.
     *
     * @param dockableClass the Dockable class
     * @return the previously registered default Dockable preferences if any, else null
     */
    public DockablePreferences unregisterDefaultDockablePreferences(Class<?> dockableClass) {
        return dockablePreferencesManager.unregisterDefaultDockablePreferences(dockableClass);
    }

    /**
     * Gets the Dockable preferences for the provided Dockable instance.
     *
     * @param dockable the Dockable instance
     * @return the Dockable preferences for the provided Dockable instance
     */
    public DockablePreferences getDockablePreferences(D dockable) {
        return dockablePreferencesManager.getDockablePreferences(dockable);
    }

    private D createEditor(Object content, Class<? extends D> editorType) throws IllegalAccessException, SecurityException, InvocationTargetException, InstantiationException, IllegalArgumentException,
            NoSuchMethodException {
        Constructor<? extends D> editorConstructor = editorType.getConstructor(content.getClass());
        return editorConstructor.newInstance(content);
    }

    /**
     * Creates and registers a view {@link DockableEntry}.
     *
     * @param viewType the view type
     * @param displayName the display name to be used as the title
     * @param icon the icon name pattern
     * @param resourceLoader the resource loader
     * @return the view Dockable entry
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
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

    /**
     * Registers an editor for an non-null unique key of some content. A unique key can e.g. be a file path or a business key of a business object.
     *
     * @param uniqueKey the unique id
     * @param dockableEntry the editor Dockable entry
     */
    public void registerEditor(Object uniqueKey, E dockableEntry) {
        if (uniqueKey != null && dockableEntry != null && dockableEntry.getKind() == DockableKind.EDITOR) {
            editorRegistry.registerEditor(uniqueKey, dockableEntry);
        }
    }

    /**
     * Unregisters an editor Dockable entry.
     *
     * @param editorEntry the editor Dockable entry to unregister
     * @throws Exception
     */
    public void unregisterEditor(E editorEntry) throws Exception {
        if (editorEntry != null && editorEntry.getKind() == DockableKind.EDITOR) {
            final D dockable = editorEntry.getDockable();
            dockableDataManager.unregisterDockableData(dockable);
            dockablePreferencesManager.unregisterDockablePreferences(dockable);
            editorRegistry.unregisterEditor(editorEntry);
            closeAutoCloseable(dockable);
        }
    }

    /**
     * Unregisters a view Dockable entry.
     *
     * @param viewEntry the view Dockable entry to unregister
     * @throws Exception
     */
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

    /**
     * {@inheritDoc }
     */
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
