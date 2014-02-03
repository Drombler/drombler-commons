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
 *
 * @author puce
 */


public class ContextWrapper implements Context {

    private final Context context;

    public ContextWrapper(Context context) {
        this.context = context;
    }

    @Override
    public <T> T find(Class<T> type) {
        return context.find(type);
    }

    @Override
    public <T> Collection<? extends T> findAll(Class<T> type) {
        return context.findAll(type);
    }

    @Override
    public void addContextListener(Class<?> type, ContextListener listener) {
        context.addContextListener(type, listener);
    }

    @Override
    public void removeContextListener(Class<?> type, ContextListener listener) {
        context.removeContextListener(type, listener);
    }

}
