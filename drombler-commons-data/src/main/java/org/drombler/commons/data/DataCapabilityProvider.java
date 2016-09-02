package org.drombler.commons.data;

/**
 * A data capability provider.
 *
 * This class can be used to register generic data capabilities to data handlers. Eg. {@link Openable} is a generic data capability which provides a mechanism to open a data handler in an editor.
 *
 * @param <T> the data capability type
 * @see Openable
 * @author puce
 */
// * @see DocumentHandler
// * @see BusinessObjectHandler
public interface DataCapabilityProvider<T> {

    /**
     * Gets a data capability for the given data handler.
     *
     * @param dataHandler the data handler
     * @return a data capability for the given data handler
     */
//     * @see DocumentHandler
//     * @see BusinessObjectHandler
    T getDataCapability(Object dataHandler);
}
