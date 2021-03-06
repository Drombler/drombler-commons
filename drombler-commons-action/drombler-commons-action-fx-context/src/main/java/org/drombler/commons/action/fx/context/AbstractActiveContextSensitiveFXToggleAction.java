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

import org.drombler.commons.action.fx.AbstractFXToggleAction;
import org.drombler.commons.action.fx.FXToggleAction;
import org.drombler.commons.context.ActiveContextSensitive;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.context.ContextListener;

/**
 * A base class for
 * {@link ActiveContextSensitive} {@link FXToggleAction}s.
 *
 * @param <T> the type of the action command
 */
public abstract class AbstractActiveContextSensitiveFXToggleAction<T> extends AbstractFXToggleAction implements ActiveContextSensitive, AutoCloseable {

    private Context activeContext;
    private final Class<T> type;
    private final ContextListener<T> contextListener = this::contextChanged;

    /**
     * Creates a new instance of this class.
     *
     * @param type the type of the action command
     */
    public AbstractActiveContextSensitiveFXToggleAction(Class<T> type) {
        this.type = type;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void setActiveContext(Context activeContext) {
        this.activeContext = activeContext;
        this.getActiveContext().addContextListener(getType(), contextListener);
        contextChanged(new ContextEvent<>(getActiveContext(), getType()));
    }

    /**
     * The callback method which gets called when the content for the action
     * command type in the active context changes.
     *
     * To get the latest content use something like:
     * {@code T myActionCommand = getActiveContext().find(contextEvent.getType());}
     *
     * @param event the context event
     */
    protected abstract void contextChanged(ContextEvent<T> event);

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        this.getActiveContext().removeContextListener(getType(), contextListener);
        this.activeContext = null;
    }

    /**
     * Gets the active context.
     *
     * @return the active context
     * @see ActiveContextSensitive
     */
    public Context getActiveContext() {
        return activeContext;
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
