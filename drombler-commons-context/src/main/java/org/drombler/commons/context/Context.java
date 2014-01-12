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

    <T> T find(Class<T> type);

    //TODO: retunr List? 
    //TODO: return <T> instead of <? extends T>?
    <T> Collection<? extends T> findAll(Class<T> type);

    void addContextListener(Class<?> type, ContextListener listener);

    void removeContextListener(Class<?> type, ContextListener listener);
}
