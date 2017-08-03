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
import java.util.Collections;

/**
 * A utility class for {@link Context}s.
 *
 * @author puce
 */
public final class Contexts {

    private static Context EMPTY_CONTEXT;

    private Contexts() {
    }

    /**
     * Returns an immutable empty {@link Context}.
     *
     * @return an immutable empty Context
     */
    public static Context emptyContext() {
        if (EMPTY_CONTEXT == null) {
            EMPTY_CONTEXT = new Context() {

                @Override
                public <T> T find(Class<T> type) {
                    return null;
                }

                @Override
                public <T> Collection<? extends T> findAll(Class<T> type) {
                    return Collections.emptyList();
                }

                @Override
                public void addContextListener(Class<?> type, ContextListener listener) {
                    // there will be no changes -> nothing to do
                }

                @Override
                public void removeContextListener(Class<?> type, ContextListener listener) {
                    // there will be no changes -> nothing to do
                }
            };
        }
        return EMPTY_CONTEXT;
    }

    /**
     * Gets the local {@link Context} of a {@link LocalContextProvider}.
     *
     * @param localContextProvider a LocalContextProvider
     * @return the local Context of the provided LocalContextProvider
     * @throws ClassCastException if localContextProvider does not implement LocalContextProvider
     */
    public static Context getLocalContext(Object localContextProvider) {
        return ((LocalContextProvider) localContextProvider).getLocalContext();
    }
    /**
     * Finds an instance of the specified type in the local context of the provided {@link LocalContextProvider}. If the local context has more than one instance of the specified type, the first one
     * found will be returned.
     *
     * @param <T> the specified type
     * @param localContextProvider a LocalContextProvider
     * @param type the specified type
     * @return the first instance found in this context with the specified type, or {@code null} if no instance was found or if localContextProvider does not implement LocalContextProvider.
     *
     * TODO: return Optional?
     */
    public static <T> T find(Object localContextProvider, Class<T> type) {
        if (localContextProvider instanceof LocalContextProvider) {
            Context localContext = getLocalContext(localContextProvider);
            return localContext.find(type);
        } else {
            return null;
        }
    }

    public static Context createFixedContext(Collection<?> content) {
        SimpleContextContent contextContent = new SimpleContextContent();
        Context fixedContext = new SimpleContext(contextContent);
        contextContent.addAll(content);
        return fixedContext;
    }

}
