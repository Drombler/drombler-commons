/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * A {@link Context} which proxies other contexts.
 *
 * @author puce
 */
public class ProxyContext extends AbstractContext {

    private final List<Context> contexts = new ArrayList<>();

    /**
     * Creates a new instance of this class.
     */
    public ProxyContext() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> T find(Class<T> type) {
        for (Context context : contexts) {
            T result = context.find(type);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> Collection<T> findAll(Class<T> type) {
        List<T> result = new ArrayList<>();

        contexts.forEach(context -> result.addAll(context.findAll(type)));

        return result;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> void addContextListener(Class<T> type, ContextListener<T> listener) {
        super.addContextListener(type, listener);

        contexts.forEach(context -> context.addContextListener(type, listener));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> void removeContextListener(Class<T> type, ContextListener<T> listener) {
        super.removeContextListener(type, listener);
        contexts.forEach(context -> context.removeContextListener(type, listener));
    }

//    @Override
//    public <T> void track(Class<T> type, ContextListener<T> listener) {
//        if (contexts != null) {
//            for (Context context : contexts) {
//                context.track(type, listener);
//            }
//        }
//    }
    /**
     * Adds another {@link Context} to be proxied by this context.
     *
     * @param context a Context to be proxied
     */
    public void addContext(Context context) {
        addContextOnly(context);
        fireContextEvents(Arrays.asList(context));
    }

    /**
     * Adds a collection of {@link Context}s to be proxied by this context
     *
     * @param contexts the contexts to be proxied
     */
    public void addContexts(Collection<Context> contexts) {
        contexts.forEach(this::addContextOnly);
        fireContextEvents(contexts);
    }

    private void addContextOnly(Context context) {
        contexts.add(context);
        getListeners().entrySet().forEach(entry
                -> entry.getValue().forEach(contextListener
                        -> addContextListener(context, entry.getKey(), contextListener)));
    }

    @SuppressWarnings("unchecked")
    private <T> void addContextListener(Context context, Class<?> type, ContextListener<?> contextListener) {
        context.addContextListener((Class<T>) type, (ContextListener<T>) contextListener);
    }

    /**
     * Removes a {@link Context} from being proxied by this context
     *
     * @param context the context to removed
     */
    public void removeContext(Context context) {
        removeContextOnly(context);
        fireContextEvents(Arrays.asList(context));
    }

    private void removeContextOnly(Context context) {
        contexts.remove(context);
        getListeners().entrySet().forEach(entry
                -> entry.getValue().forEach(contextListener
                        -> removeContextListener(context, entry.getKey(), contextListener)));
    }

    @SuppressWarnings("unchecked")
    private <T> void removeContextListener(Context context, Class<?> type, ContextListener<?> contextListener) {
        context.removeContextListener((Class<T>) type, (ContextListener<T>) contextListener);
    }

    /**
     * Sets the contexts to be proxied by this context. This will remove all contexts registered before.
     *
     * @param contexts the contexts to be proxied
     */
    public void setContexts(Context... contexts) {
        setContexts(Arrays.asList(contexts));
    }

    /**
     * Sets the contexts to be proxied by this context. This will remove all contexts registered before.
     *
     * @param contexts the contexts to be proxied
     */
    public void setContexts(List<? extends Context> contexts) {
        List<Context> contextsToRemove = new ArrayList<>(this.contexts);
        contextsToRemove.removeAll(contexts);

        contextsToRemove.forEach(this::removeContextOnly);

        List<Context> contextsToAdd = new ArrayList<>(contexts);
        contextsToAdd.removeAll(this.contexts);

        contextsToAdd.forEach(this::addContextOnly);

        List<Context> changedContexts = new ArrayList<>(contextsToRemove);
        changedContexts.addAll(contextsToAdd);

        fireContextEvents(changedContexts);
    }

    /**
     * Indicates if this proxy has some contexts.
     *
     * @return true, if the proxy has no contexts, else false
     */
    public boolean isEmpty() {
        return contexts.isEmpty();
    }

    private void fireContextEvents(Collection<Context> changedContexts) {
        for (Class<?> type : getListeners().keySet()) {
            for (Context context : changedContexts) {
                if (!context.findAll(type).isEmpty()) {
                    fireContextEvent(type);
                    break; // TODO: correct?
                }
            }
        }
    }
}
