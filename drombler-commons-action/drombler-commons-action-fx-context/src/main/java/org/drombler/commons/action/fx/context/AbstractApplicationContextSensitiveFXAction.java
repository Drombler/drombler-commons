/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2018 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.action.fx.context;

import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.action.fx.FXAction;
import org.drombler.commons.context.ApplicationContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;

/**
 * A base class for {@link ApplicationContextSensitive} {@link FXAction}s.
 *
 * @param <T> the type of the action command
 */
public abstract class AbstractApplicationContextSensitiveFXAction<T> extends AbstractFXAction implements ApplicationContextSensitive, AutoCloseable {

    private Context applicationContext;
    private final Class<T> type;
    private final ContextListener<T> contextListener = this::contextChanged;

    /**
     * Creates a new instance of this class.
     *
     * @param type the type of the action command
     */
    public AbstractApplicationContextSensitiveFXAction(Class<T> type) {
        this.type = type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setApplicationContext(Context applicationContext) {
        this.applicationContext = applicationContext;
        this.getApplicationContext().addContextListener(getType(), contextListener);
        contextChanged(new ContextEvent<>(getApplicationContext(), getType()));
    }

    /**
     * The callback method which gets called when the content for the action
     * command type in the application wide context changes.
     *
     * To get the latest content use something like:
     * {@code Collection<? extends T> myActionCommands = getApplicationContext().findAll(event.getType());}
     *
     * @param event the context event
     */
    protected abstract void contextChanged(ContextEvent<T> event);

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        this.getApplicationContext().removeContextListener(getType(), contextListener);
        this.applicationContext = null;
    }

    /**
     * Gets the application wide context.
     *
     * @return the the application wide context
     * @see ApplicationContextSensitive
     */
    public Context getApplicationContext() {
        return applicationContext;
    }

    /**
     * Gets the type of the action command
     *
     * @return the type of the action command
     */
    public Class<T> getType() {
        return type;
    }
}
