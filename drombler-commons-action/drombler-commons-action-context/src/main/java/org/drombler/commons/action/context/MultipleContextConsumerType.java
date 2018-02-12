package org.drombler.commons.action.context;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import org.drombler.commons.context.ContextEvent;

/**
 *
 * @author puce
 */
 class MultipleContextConsumerType<T> implements ContextConsumerType<T> {

    private final Consumer<T> onActionHandler;
    private Collection<? extends T> actionCommandCollection = Collections.emptyList();

    public MultipleContextConsumerType(Consumer<T> onActionHandler) {
        this.onActionHandler = onActionHandler;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void find(ContextEvent<T> contextEvent) {
//        actionCommandCollection = contextEvent.findAll();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void onAction() {
        List<T> currentActionCommandCollection = new ArrayList<>(actionCommandCollection); // protect against modification during iteration TODO: needed?
        currentActionCommandCollection.forEach(onActionHandler::accept);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean isEnabled() {
        return !actionCommandCollection.isEmpty();
    }
}
