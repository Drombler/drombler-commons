package org.drombler.commons.docking;

/**
 *
 * @author puce
 * @param <D>
 * @param <DATA>
 * @param <E>
 */
public interface EditorRegistry<D, DATA extends DockableData, E extends DockableEntry<D, DATA>> {

    boolean containsEditor(Object uniqueKey);

    E getEditor(Object uniqueKey);

    void registerEditor(Object uniqueKey, E editorEntry);

    void unregisterEditor(E editorEntry);

}
