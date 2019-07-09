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
 * A simple context with a writable content.
 */
// TODO: Better name?
public class SimpleContext extends AbstractContext {

    private final SimpleContextContent contextContent;

    /**
     * Creates a new instance of this class.
     *
     * @param contextContent a writable content
     */
    public SimpleContext(SimpleContextContent contextContent) {
        this.contextContent = contextContent;
        this.contextContent.setContext(this);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> T find(Class<T> type) {
        return contextContent.find(type);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> Collection<T> findAll(Class<T> type) {
        return contextContent.findAll(type);
    }

}
