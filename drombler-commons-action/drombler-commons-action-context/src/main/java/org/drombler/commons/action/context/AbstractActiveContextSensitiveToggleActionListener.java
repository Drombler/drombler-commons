package org.drombler.commons.action.context;

import org.drombler.commons.action.AbstractToggleActionListener;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;

/**
 *
 * @author puce
 * @param <T> the type of the action command
 */
public abstract class AbstractActiveContextSensitiveToggleActionListener<T, E> extends AbstractToggleActionListener<E> implements ActiveContextSensitive, AutoCloseable {

    private Context activeContext;
    private final Class<T> type;
    private final ContextListener<T> contextListener = this::contextChanged;

    public AbstractActiveContextSensitiveToggleActionListener(Class<T> type) {
        this.type = type;
    }


    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.getActiveContext().addContextListener(getType(), contextListener);
        contextChanged(new ContextEvent<>(getActiveContext(), getType()));
    }

    protected abstract void contextChanged(ContextEvent<T> event);

    @Override
    public void close() {
        this.getActiveContext().removeContextListener(getType(), contextListener);
        this.activeContext = null;
    }

    /**
     * @return the activeContext
     */
    public Context getActiveContext() {
        return activeContext;
    }

    /**
     * @return the type
     */
    public Class<T> getType() {
        return type;
    }
}
