/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.data;

import org.drombler.commons.context.LocalContextProvider;

/**
 * A data capability to open data handlers in editors.
 *
 * This data capability is provided by a {@link DataCapabilityProvider}.
 *
 * To fully integrate with the framework register the instance provided for the data handler in it's local context.
 *
 * @see LocalContextProvider
 * @see DataHandler
 * @author puce
 */
// * @see DocumentHandler
// * @see BusinessObjectHandler
public interface Openable {

    /**
     * Opens the data handler in an editor.
     */
    void open();
}
