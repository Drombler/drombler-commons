package org.drombler.commons.fx.beans.binding;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.beans.WeakListener;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

/**
 * 
 * @author puce
 */
public final class CollectionBindings {

    private CollectionBindings() {
    }

    /**
     * 
     * @param <S>
     * @param <T>
     * @param targetList
     * @param sourceList
     * @param mapper 
     */
    public static <S, T> void bindContent(List<T> targetList, ObservableList<? extends S> sourceList, Function<? super S, ? extends T> mapper) {
        List<? extends T> elements = mapList(sourceList, mapper);
        setContent(targetList, elements);
        ListContentListener<S, T> contentListener = new ListContentListener<>(targetList, mapper);
        sourceList.removeListener(contentListener);
        sourceList.addListener(contentListener);
    }

    /**
     * 
     * @param <S>
     * @param <T>
     * @param targetList
     * @param sourceList 
     */
    public static <S, T> void unbindContent(List<T> targetList, ObservableList<? extends S> sourceList) {
        sourceList.removeListener(new ListContentListener<>(targetList, null));
    }

    private static <T> void setContent(List<T> targetList, List<? extends T> elements) {
        if (targetList instanceof ObservableList) {
            ((ObservableList<T>) targetList).setAll(elements);
        } else {
            targetList.clear();
            targetList.addAll(elements);
        }
    }

    /**
     * 
     * @param <S>
     * @param <T>
     * @param targetList
     * @param targetChildListExtractor
     * @param sourceList
     * @param sourceChildListExtractor
     * @param mapper 
     */
    public static <S, T> void bindTreeContent(List<T> targetList, Function<? super T, ? extends List<T>> targetChildListExtractor,
            ObservableList<? extends S> sourceList, Function<? super S, ? extends ObservableList<? extends S>> sourceChildListExtractor,
            Function<? super S, ? extends T> mapper) {
        List<? extends T> elements = mapList(sourceList, mapper);
        bindSubTreeContent(elements, targetChildListExtractor, sourceList, sourceChildListExtractor, mapper);  // recursion

        setContent(targetList, elements);
        TreeContentListener<S, T> treeContentListener = new TreeContentListener<>(targetList, targetChildListExtractor, sourceChildListExtractor, mapper);
        sourceList.removeListener(treeContentListener);
        sourceList.addListener(treeContentListener);
    }

    /**
     * 
     * @param <S>
     * @param <T>
     * @param targetList
     * @param targetChildListExtractor
     * @param sourceList
     * @param sourceChildListExtractor 
     */
    public static <S, T> void unbindTreeContent(List<T> targetList, Function<? super T, ? extends List<T>> targetChildListExtractor,
            ObservableList<? extends S> sourceList, Function<? super S, ? extends ObservableList<? extends S>> sourceChildListExtractor) {
        unbindSubTreeContent(targetList, targetChildListExtractor, sourceList, sourceChildListExtractor);  // recursion
        sourceList.removeListener(new TreeContentListener<>(targetList, targetChildListExtractor, sourceChildListExtractor, null));
    }

    private static <S, T> void bindSubTreeContent(List<? extends T> elements, Function<? super T, ? extends List<T>> targetChildListExtractor,
            List<? extends S> sourceList, Function<? super S, ? extends ObservableList<? extends S>> sourceChildListExtractor,
            Function<? super S, ? extends T> mapper) {
        Iterator<? extends S> sourceListIterator = sourceList.iterator();
        Iterator<? extends T> elementsIterator = elements.iterator();
        while (sourceListIterator.hasNext()) {
            S sourceElement = sourceListIterator.next();
            T targetElement = elementsIterator.next();
            ObservableList<? extends S> sourceChildList = sourceChildListExtractor.apply(sourceElement);
            if (!sourceChildList.isEmpty()) {
                bindTreeContent(targetChildListExtractor.apply(targetElement), targetChildListExtractor, sourceChildList, sourceChildListExtractor, mapper); // recursion
            }
        }
    }

    private static <S, T> void unbindSubTreeContent(List<? extends T> elements, Function<? super T, ? extends List<T>> targetChildListExtractor,
            List<? extends S> sourceList, Function<? super S, ? extends ObservableList<? extends S>> sourceChildListExtractor) {
        Iterator<? extends S> sourceListIterator = sourceList.iterator();
        Iterator<? extends T> elementsIterator = elements.iterator();
        while (sourceListIterator.hasNext()) {
            S sourceElement = sourceListIterator.next();
            T targetElement = elementsIterator.next();
            ObservableList<? extends S> sourceChildList = sourceChildListExtractor.apply(sourceElement);
            if (!sourceChildList.isEmpty()) {
                unbindTreeContent(targetChildListExtractor.apply(targetElement), targetChildListExtractor, sourceChildList, sourceChildListExtractor); // recursion
            }
        }
    }

    private static <S, T> List<? extends T> mapList(List<? extends S> sourceList, Function<? super S, ? extends T> mapper) {
        return sourceList.stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    private static class ListContentListener<S, T> implements ListChangeListener<S>, WeakListener {

        private final WeakReference<List<T>> listReference;
        private final Function<? super S, ? extends T> mapper;

        public ListContentListener(List<T> targetList, Function<? super S, ? extends T> mapper) {
            this.listReference = new WeakReference<>(targetList);
            this.mapper = mapper;
        }

        public Function<? super S, ? extends T> getMapper() {
            return mapper;
        }

        @Override
        public void onChanged(ListChangeListener.Change<? extends S> change) {
            List<T> targetList = listReference.get();
            if (targetList == null) {
                change.getList().removeListener(this);
            } else {
                while (change.next()) {
                    if (change.wasPermutated()) {
                        removeElements(targetList, change);
                        addAll(targetList, change.getFrom(), change.getList().subList(change.getFrom(), change.getTo()));
                    } else if (change.wasRemoved()) {
                        removeElements(targetList, change);
                    } else if (change.wasAdded()) {
                        List<? extends S> addedSourceSubList = change.getAddedSubList();
                        int index = change.getFrom();
                        addAll(targetList, index, addedSourceSubList);
                    }
                }
            }
        }

        protected void removeElements(List<T> targetList, Change<? extends S> change) {
            targetList.subList(change.getFrom(), change.getTo()).clear();
        }

        protected void addAll(List<T> targetList, int index, List<? extends S> addedSourceSubList) {
            List<? extends T> addedElements = mapList(addedSourceSubList, getMapper());
            targetList.addAll(index, addedElements);
        }

        @Override
        public boolean wasGarbageCollected() {
            return listReference.get() == null;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 17 * hash + Objects.hashCode(this.listReference.get());
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ListContentListener)) {
                return false;
            }

            final ListContentListener<?, ?> other = (ListContentListener<?, ?>) obj;
            return this.listReference.get() == other.listReference.get();
        }

    }

    private static class TreeContentListener<S, T> extends ListContentListener<S, T> {

        private final Function<? super S, ? extends ObservableList<? extends S>> sourceChildListExtractor;
        private final Function<? super T, ? extends List<T>> targetChildListExtractor;

        public TreeContentListener(List<T> targetList, Function<? super T, ? extends List<T>> targetChildListExtractor,
                Function<? super S, ? extends ObservableList<? extends S>> sourceChildListExtractor,
                Function<? super S, ? extends T> mapper) {
            super(targetList, mapper);
            this.targetChildListExtractor = targetChildListExtractor;
            this.sourceChildListExtractor = sourceChildListExtractor;
        }

        @Override
        protected void removeElements(List<T> targetList, Change<? extends S> change) {
            final List<T> removedELements = targetList.subList(change.getFrom(), change.getTo());
            unbindSubTreeContent(removedELements, targetChildListExtractor, change.getRemoved(), sourceChildListExtractor);
            removedELements.clear();
        }

        @Override
        protected void addAll(List<T> targetList, int index, List<? extends S> addedSourceSubList) {
            List<? extends T> addedElements = CollectionBindings.<S, T>mapList(addedSourceSubList, getMapper());
            bindSubTreeContent(addedElements, targetChildListExtractor, addedSourceSubList, sourceChildListExtractor, getMapper());
            targetList.addAll(index, addedElements);
        }

    }
}
