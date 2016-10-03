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

/**
 * A data capability provider.
 *
 * This class can be used to register generic data capabilities to data handlers. Eg. {@link Openable} is a generic data capability which provides a mechanism to open a data handler in an editor.
 *
 * Note: This interface might change in future releases.
 *
 * @param <T> the data capability type
 * @see Openable
 * @see DataHandler
 * @author puce
 */
// * @see DocumentHandler
// * @see BusinessObjectHandler
public interface DataCapabilityProvider<T> {

    /**
     * Gets a data capability for the given {@link DataHandler}.
     *
     * @param dataHandler the data handler
     * @return a data capability for the given data handler
     */
//     * @see DocumentHandler
//     * @see BusinessObjectHandler
    T getDataCapability(DataHandler<?> dataHandler);
}
