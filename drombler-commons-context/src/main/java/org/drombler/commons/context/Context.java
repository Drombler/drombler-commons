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

import java.util.Collection;

/**
 * A Context to find context-sensitive data.
 *
 * @author puce
 */
public interface Context {

    /**
     * Finds an instance of the specified type in this context. If this context has more than one instance of the
     * specified type, the first one found will be returned.
     *
     * @param <T> the specified type
     * @param type the specified type
     * @return the first instance found in this context with the specified type, or {@code null} if no instance was
     * found.
     *
     * TODO: return Optional?
     */
    <T> T find(Class<T> type);

    /**
     * Finds all instances of the specified type in this context.
     *
     * @param <T> the specified type
     * @param type the specified type
     * @return a collection with all instances found in this context with the specified type, or an empty collection if
     * no instance was found
     *
     * TODO: return {@code List} instead of {@code Collection}? <br>
     * TODO: return {@code <T>} instead of {@code <? extends T>}?
     */
    <T> Collection<? extends T> findAll(Class<T> type);

    /**
     * Registers a {@link ContextListener} for a specified type.
     *
     * The listener will be notified when instances of the specified type get added or removed.
     *
     * @param type the type to listen for
     * @param listener the ContextListener
     */
    void addContextListener(Class<?> type, ContextListener listener);

    /**
     * Unregisters a {@link ContextListener} for a specified type.
     *
     * @param type the type to listen for
     * @param listener the ContextListener
     */
    void removeContextListener(Class<?> type, ContextListener listener);
}
