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

import java.util.EventListener;

/**
 * A listener to listen for changes in a {@link Context}.
 *
 * @param <T> the type of objects to listen for in the context
 * @author puce
 */
public interface ContextListener<T> extends EventListener {

    /**
     * A call-back method which gets called when a {@link Context} changed.
     *
     * @param event the context event
     */
    void contextChanged(ContextEvent<T> event);
}
