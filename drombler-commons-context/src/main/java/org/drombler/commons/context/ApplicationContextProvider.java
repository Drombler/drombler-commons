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

/**
 * The application context provider provides the application wide context.
 */
//  TODO: Good name? GlobalContextProvider?
//  TODO: Good package/ bundle? SPI?
//  TODO: Replace with CDI?
//  TODO: Needed?
public interface ApplicationContextProvider {

    /**
     * Gets the application-wide context. It provides access to the combined content of all registered local Contexts.
     *
     * @return the application-wide context
     */
    Context getApplicationContext();
}
