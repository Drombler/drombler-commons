package org.drombler.commons.data;

import org.drombler.commons.context.LocalContextProvider;
import org.softsmithy.lib.util.UniqueKeyProvider;

/**
 * A data handler. A data handler usually knows how to read, save etc. a particular kind of data.
 *
 * To fully integrate with the framework it should observe registered {@link DataCapabilityProvider}s and add the found data capabilities to it's local context.
 *
 * The easiest way to implement a data handler is to extend {@link AbstractDataHandler}, but it's not required.
 *
 * @see AbstractDataHandler
 * @param <T> the type of the unique key of this data handler
 * @author puce
 */
public interface DataHandler<T> extends LocalContextProvider, UniqueKeyProvider<T> {

}
