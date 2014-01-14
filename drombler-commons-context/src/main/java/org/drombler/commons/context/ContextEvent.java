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

import java.util.EventObject;

/**
 * An event which indicates the content of a {@link Context} changed.
 *
 * @author puce
 */
public class ContextEvent extends EventObject {

    private final Context sourceContext;

    /**
     * Creates a new instance of this class.
     *
     * @param sourceContext
     */
    public ContextEvent(Context sourceContext) {
        super(sourceContext);
        this.sourceContext = sourceContext;
    }

    /**
     * {@inheritDoc }
     *
     * @return the source Context
     */
    @Override
    public Context getSource() {
        return sourceContext;
    }

}
