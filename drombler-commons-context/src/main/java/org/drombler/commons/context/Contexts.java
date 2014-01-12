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
 *
 * @author puce
 */
public class Contexts {

    private static Context EMPTY_CONTEXT;

    private Contexts() {
    }

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
}
