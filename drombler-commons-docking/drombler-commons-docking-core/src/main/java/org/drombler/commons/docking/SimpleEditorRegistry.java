package org.drombler.commons.docking;

import java.util.HashMap;
import java.util.Map;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;
import org.drombler.commons.docking.EditorRegistry;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public class SimpleEditorRegistry<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> implements EditorRegistry<D, DATA, E> {

    private final Map<Object, E> editorEntries = new HashMap<>();
    private final Map<E, Object> uniqueKeys = new HashMap<>();


    @Override
    public boolean containsEditor(Object uniqueKey) {
        return editorEntries.containsKey(uniqueKey);
    }

    @Override
    public E getEditor(Object uniqueKey) {
        return editorEntries.get(uniqueKey);
    }

    @Override
    public void registerEditor(Object uniqueKey, E editorEntry) {
        editorEntries.put(uniqueKey, editorEntry);
        uniqueKeys.put(editorEntry, uniqueKey);
    }

    @Override
    public void unregisterEditor(E editorEntry) {
        Object uniqueKey = uniqueKeys.remove(editorEntry);
        editorEntries.remove(uniqueKey);
    }

}
