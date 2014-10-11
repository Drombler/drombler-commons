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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A simple writable in-memory context content.
 *
 * @author puce
 * <br>
 * TODO: Better name?
 */
public class SimpleContextContent {

    private final Map<Class<?>, List<Object>> objects = new HashMap<>();
    private AbstractContext context;

    /**
     * Adds an object.
     *
     * @param obj the object to be added
     */
    public void add(Object obj) {
        Set<Class<?>> types = getTypes(obj);
        types.forEach(type -> add(type, obj));

        fireContextEvents(types);
    }

    private void fireContextEvents(Set<Class<?>> types) {
        if (context != null) {
            types.forEach(type -> context.fireContextEvent(type));
        }
    }

    private void add(Class<?> type, Object obj) {
        if (!objects.containsKey(type)) {
            objects.put(type, new ArrayList<>());
        }
        objects.get(type).add(obj);
    }

    /**
     * Removed an object.
     *
     * @param obj the object to be removed.
     */
    public void remove(Object obj) {
        if (obj != null) {
            Set<Class<?>> types = getTypes(obj);
            types.forEach(type -> remove(type, obj));

            fireContextEvents(types);
        }
    }

    private void remove(Class<?> type, Object obj) {
        if (objects.containsKey(type)) {
            objects.get(type).remove(obj);
        }
    }

    private Set<Class<?>> getTypes(Object obj) {
        Set<Class<?>> types = new HashSet<>();
        Class<?> type = obj.getClass();
        while (type != null) {
            types.add(type);
            types.addAll(Arrays.asList(type.getInterfaces()));
            type = type.getSuperclass();
        }
        return types;
    }

    /*package-private*/ void setContext(AbstractContext context) {
        if (this.context != null) {
            throw new IllegalStateException("This ContextContent is already attached to a Context. "
                    + "A ContextContent can be attached only to one Context!");
        }
        this.context = context;
    }


    /*package-private*/ <T> T find(Class<T> type) {
        if (objects.containsKey(type)) {
            List<Object> objs = objects.get(type);
            if (!objs.isEmpty()) {
                return type.cast(objs.get(0));
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    /*package-private*/ <T> Collection<? extends T> findAll(Class<T> type) {
        if (objects.containsKey(type)) {
            return (Collection<? extends T>) Collections.unmodifiableList(objects.get(type));
        }
        return Collections.emptyList();
    }
}
