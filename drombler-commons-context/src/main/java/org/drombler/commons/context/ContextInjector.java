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

/**
 * TODO: Replace with CDI?
 *
 * @author puce
 */
public class ContextInjector {

    private final ActiveContextProvider activeContextProvider;
    private final ApplicationContextProvider applicationContextProvider;

    public ContextInjector(ActiveContextProvider activeContextProvider,
            ApplicationContextProvider applicationContextProvider) {
        this.activeContextProvider = activeContextProvider;
        this.applicationContextProvider = applicationContextProvider;
    }

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
