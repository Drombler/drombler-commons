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

import org.softsmithy.lib.util.Injector;

/**
 * The ContextInjector injects the active {@link Context} to Objects, which implement the {@link ActiveContextSensitive}
 * interface and the application {@link Context} to Objects, which implement the {@link ApplicationContextSensitive}
 * interface.
 *
 * TODO: Replace with CDI?
 *
 * @author puce
 */
public class ContextInjector implements Injector<Object> {

    private final ActiveContextProvider activeContextProvider;
    private final ApplicationContextProvider applicationContextProvider;

    /**
     * Creates a new instance of this class.
     *
     * @param activeContextProvider a provider, which provides the active context
     * @param applicationContextProvider a provider, which provides the application context
     */
    public ContextInjector(ActiveContextProvider activeContextProvider,
            ApplicationContextProvider applicationContextProvider) {
        this.activeContextProvider = activeContextProvider;
        this.applicationContextProvider = applicationContextProvider;
    }

    /**
     * Creates a new instance of this class.
     *
     * @param contextManager a {@link ContextManager}
     */
    public ContextInjector(ContextManager contextManager) {
        this(contextManager, contextManager);
    }

    /**
     * Injects the active {@link Context} to Objects, which implement the     * {@link ActiveContextSensitive} interface and the application {@link Context} to Objects, which implement the
     * {@link ApplicationContextSensitive} interface.
     *
     * @param target the target Object
     */
    @Override
    public void inject(Object target) {
        if (target instanceof ActiveContextSensitive) {
            ((ActiveContextSensitive) target).setActiveContext(activeContextProvider.getActiveContext());
        }
        if (target instanceof ApplicationContextSensitive) {
            ((ApplicationContextSensitive) target).
                    setApplicationContext(applicationContextProvider.getApplicationContext());
        }
    }
}
