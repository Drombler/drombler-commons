package org.drombler.commons.docking.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.drombler.commons.docking.DockableData;
import org.drombler.commons.docking.DockableEntry;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public class EditorRegistry<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> {

    private final Map<Object, E> editorEntries = new HashMap<>();
    private final Map<E, Object> uniqueKeys = new HashMap<>();


    public boolean containsEditor(Object uniqueKey) {
        return editorEntries.containsKey(uniqueKey);
    }

    public E getEditor(Object uniqueKey) {
        return editorEntries.get(uniqueKey);
    }

    public void registerEditor(Object uniqueKey, E editorEntry) {
        editorEntries.put(uniqueKey, editorEntry);
        uniqueKeys.put(editorEntry, uniqueKey);
    }

    public void unregisterEditor(E editorEntry) {
        Object uniqueKey = uniqueKeys.remove(editorEntry);
        editorEntries.remove(uniqueKey);
    }

    public Set<E> clear() {
        Set<E> allEditors = new HashSet<>(uniqueKeys.keySet());
        uniqueKeys.clear();
        editorEntries.clear();
        return allEditors;
    }

}
