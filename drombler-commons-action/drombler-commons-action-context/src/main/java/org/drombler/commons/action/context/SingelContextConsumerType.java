package org.drombler.commons.action.context;

import java.util.function.Consumer;
import org.drombler.commons.context.ContextEvent;

/**
 *
 * @author puce
 */


 class SingelContextConsumerType<T> implements ContextConsumerType<T> {

    private final Consumer<T> onActionHandler;
    private T actionCommand;

    public SingelContextConsumerType(Consumer<T> onActionHandler) {
        this.onActionHandler = onActionHandler;
    }

    @Override
    public void find(ContextEvent<T> contextEvent) {
//        actionCommand = contextEvent.find();
    }

    @Override
    public void onAction() {
        onActionHandler.accept(actionCommand);
    }

    @Override
    public boolean isEnabled() {
        return actionCommand != null;
    }

}
