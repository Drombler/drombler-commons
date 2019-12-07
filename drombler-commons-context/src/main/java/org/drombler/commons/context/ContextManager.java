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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This {@link Context} manager allows to register local contexts for objects and manages the Application Context and the current Active Context.<br>
 * <br>
 * The <b>Active Context</b> provides access to the content of the currently active local Context.<br>
 * <br>
 * The <b>Application-wide Context</b> provides access to the combined content of all registered local Contexts.
 *
 * @author puce
 */
public class ContextManager implements ActiveContextProvider, ApplicationContextProvider {

    private static final Logger LOG = LoggerFactory.getLogger(ContextManager.class);

    private final ProxyContext applicationContext = new ProxyContext();
    private final ProxyContext activeContext = new ProxyContext();
    private final Context applicationContextWrapper = new ContextWrapper(applicationContext);
    private final Context activeContextWrapper = new ContextWrapper(activeContext);
    // TODO use Identity or Weak HashMap?
    private final Map<Object, LocalProxyContext> localContexts = new HashMap<>();
    private Object activeObject = null;

    /**
     * Creates a new instance of this class.
     */
    public ContextManager() {
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Context getApplicationContext() {
        return applicationContextWrapper;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Context getActiveContext() {
        return activeContextWrapper;
    }

    /**
     * Sets a local context active. Its content can be accessed from the Active Context. The local context of the specified object has to be registered prior to calling this method.
     *
     * @param obj the object whose local context should be set active
     */
    // TODO clear active context
    public void setLocalContextActive(Object obj) {
        LOG.debug("Start setLocalContextActive...");
        if (localContexts.containsKey(obj)) {
            activeObject = obj;
            activeContext.setContexts(localContexts.get(obj));
            LOG.debug("Active context changed!");
        }
        LOG.debug("End setLocalContextActive.");
    }

    /**
     * Registers a local context for the given Object. Its content will be available from the application-wide context and it can be set active afterwards.
     *
     * @param obj an object
     * @param context the local context of the object
     */
    public void registerLocalContext(Object obj, LocalProxyContext context) {
        if (context == null) {
            throw new IllegalArgumentException("context must not be null!");
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

    /**
     * Registers the local context of the given object if it implements {@link LocalContextProvider}.
     *
     * Its content will be available from the application-wide context and it can be set active afterwards.
     *
     * @param obj an object
     */
    public void registerLocalContext(Object obj) {
        if (obj instanceof LocalContextProvider) {
            LocalProxyContext localProxyContext = LocalProxyContext.createLocalProxyContext(obj);
            registerLocalContext(obj, localProxyContext);
        }
    }

    /**
     * Unregisters a local context. Its content won't be available from the application-wide context anymore and it can't be set active anymore.
     *
     * @param obj the object whose local context should be unregistered.
     * @return the registered context
     */
    public LocalProxyContext unregisterLocalContext(Object obj) {
        if (localContexts.containsKey(obj)) {
            LocalProxyContext oldContext = localContexts.remove(obj);
            activeContext.removeContext(oldContext);
            applicationContext.removeContext(oldContext);
            return oldContext;
        } else {
            return null;
        }
    }

    /**
     * Gets the local context for the specified object.
     *
     * @param obj the object
     * @return the local context of the specified object
     */
    public LocalProxyContext getLocalContext(Object obj) {
        return localContexts.get(obj);
    }

    private boolean isLocalContextActive(Object obj) {
        return activeObject == obj;
    }

}
