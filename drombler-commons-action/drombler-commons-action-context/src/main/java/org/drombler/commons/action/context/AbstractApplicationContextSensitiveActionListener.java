package org.drombler.commons.action.context;

import org.drombler.commons.action.AbstractActionListener;
import org.drombler.commons.context.ApplicationContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;

/**
 *
 * @author puce
 * @param <T> the type of the action command
 */
public abstract class AbstractApplicationContextSensitiveActionListener<T, E> extends AbstractActionListener<E> implements ApplicationContextSensitive, AutoCloseable {

    private Context applicationContext;
    private final Class<T> type;
    private final ContextListener<T> contextListener = this::contextChanged;

    public AbstractApplicationContextSensitiveActionListener(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.getApplicationContext().addContextListener(getType(), contextListener);
        contextChanged(new ContextEvent<>(getApplicationContext(), getType()));
    }

    protected abstract void contextChanged(ContextEvent<T> event);

    @Override
    public void close() {
        this.getApplicationContext().removeContextListener(getType(), contextListener);
        this.applicationContext = null;
    }

    /**
     * @return the applicationContext
     */
    public Context getApplicationContext() {
        return applicationContext;
    }

    /**
     * @return the type
     */
    public Class<T> getType() {
        return type;
    }
}
