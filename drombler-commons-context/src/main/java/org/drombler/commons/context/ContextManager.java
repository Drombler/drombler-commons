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

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author puce
 */
public class ContextManager implements ActiveContextProvider, ApplicationContextProvider {

    private final ProxyContext applicationContext = new ProxyContext();
    private final ProxyContext activeContext = new ProxyContext();
    private final Context applicationContextWrapper = new ContextWrapper(applicationContext);
    private final Context activeContextWrapper = new ContextWrapper(activeContext);
    // TODO use Identity or Weak HashMap?
    private final Map<Object, Context> localContexts = new HashMap<>();
    private Object activeObject = null;

    @Override
    public Context getApplicationContext() {
        return applicationContextWrapper;
    }

    @Override
    public Context getActiveContext() {
        return activeContextWrapper;
    }

    // TODO clear active context
    public void setLocalContextActive(Object obj) {
        if (localContexts.containsKey(obj)) {
            activeObject = obj;
            activeContext.setContexts(localContexts.get(obj));
        }
    }

    public void putLocalContext(Object obj, Context context) {
        if (context == null) {
            // TODO error message
            throw new IllegalArgumentException();
        }

        boolean isLocalContextActive = isLocalContextActive(obj);
        if (localContexts.containsKey(obj)) {
            final Context oldLocalContext = localContexts.get(obj);
            applicationContext.removeContext(oldLocalContext);
            if (isLocalContextActive) {
                activeContext.removeContext(oldLocalContext);
            }
        }

        localContexts.put(obj, context);
        applicationContext.addContext(context);

        if (isLocalContextActive) {
            activeContext.setContexts(context);
        }
    }

    public Context removeLocalContext(Object obj) {
        if (localContexts.containsKey(obj)) {
            Context oldContext = localContexts.remove(obj);
            activeContext.removeContext(oldContext);
            applicationContext.removeContext(oldContext);
            return oldContext;
        } else {
            return null;
        }
    }

    private boolean isLocalContextActive(Object obj) {
        return activeObject == obj;
    }

}
