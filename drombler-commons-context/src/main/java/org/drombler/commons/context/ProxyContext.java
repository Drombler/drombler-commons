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
import java.util.Map;

/**
 *
 * @author puce
 */
public class ProxyContext extends AbstractContext {

    private final List<Context> contexts = new ArrayList<>();

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

    @Override
    public <T> Collection<? extends T> findAll(Class<T> type) {
        List<T> result = new ArrayList<>();

        for (Context context : contexts) {
            result.addAll(context.findAll(type));
        }

        return result;
    }

    @Override
    public void addContextListener(Class<?> type, ContextListener listener) {
        super.addContextListener(type, listener);

        for (Context context : contexts) {
            context.addContextListener(type, listener);
        }
    }

    @Override
    public void removeContextListener(Class<?> type, ContextListener listener) {
        super.removeContextListener(type, listener);
        for (Context context : contexts) {
            context.removeContextListener(type, listener);
        }
    }

//    @Override
//    public <T> void track(Class<T> type, ContextListener<T> listener) {
//        if (contexts != null) {
//            for (Context context : contexts) {
//                context.track(type, listener);
//            }
//        }
//    }
    public void addContext(Context context) {
        addContextOnly(context);
        fireContextEvents(Arrays.asList(context));
    }

    private void addContextOnly(Context context) {
        contexts.add(context);
        for (Map.Entry<Class<?>, List<ContextListener>> entry : getListeners().entrySet()) {
            for (ContextListener contextListener : entry.getValue()) {
                context.addContextListener(entry.getKey(), contextListener);
            }
        }
    }

    public void removeContext(Context context) {
        removeContextOnly(context);
        fireContextEvents(Arrays.asList(context));
    }

    private void removeContextOnly(Context context) {
        contexts.remove(context);
        for (Map.Entry<Class<?>, List<ContextListener>> entry : getListeners().entrySet()) {
            for (ContextListener contextListener : entry.getValue()) {
                context.removeContextListener(entry.getKey(), contextListener);
            }
        }
    }

    public void setContexts(Context... contexts) {
        setContexts(Arrays.asList(contexts));
    }

    public void setContexts(List<? extends Context> contexts) {
        List<Context> contextsToRemove = new ArrayList<>(this.contexts);
        contextsToRemove.removeAll(contexts);

        for (Context context : contextsToRemove) {
            removeContextOnly(context);
        }

        List<Context> contextsToAdd = new ArrayList<>(contexts);
        contextsToAdd.removeAll(this.contexts);

        for (Context context : contexts) {
            addContextOnly(context);
        }

        List<Context> changedContexts = new ArrayList<>(contextsToRemove);
        changedContexts.addAll(contextsToAdd);

        fireContextEvents(changedContexts);
    }

    private void fireContextEvents(List<Context> changedContexts) {
        for (Class<?> type : getListeners().keySet()) {
            for (Context context : changedContexts) {
                if (!context.findAll(type).isEmpty()) {
                    fireContextEvent(type);
                    break;
                }
            }
        }
    }
}
