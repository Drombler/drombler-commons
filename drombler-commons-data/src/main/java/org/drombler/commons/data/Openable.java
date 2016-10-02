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
