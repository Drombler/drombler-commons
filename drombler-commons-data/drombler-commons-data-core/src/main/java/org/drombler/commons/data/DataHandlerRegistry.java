package org.drombler.commons.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.UniqueKeyProvider;

/**
 * A registry for all open data handlers with a non-null unique key.
 *
 * @author puce
 */
public class DataHandlerRegistry implements AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(DataHandlerRegistry.class);

    private final Map<Object, DataHandler<?>> dataHandlers = new HashMap<>();

    /**
     * Creates a new instance of this class.
     */
    public DataHandlerRegistry() {
    }

    /**
     * Registers a new {@link DataHandler}.<br>
     * <br>
     * Note: Always work with the returned data handler!
     *
     * @param <T> the type of the unique key of the data handler
     * @param dataHandler the data handler
     * @return the provided data handler or the already registered data handler for the unique key, if there is any
     */
    public <T> DataHandler<T> registerDataHandler(DataHandler<T> dataHandler) {
        if (dataHandler.getUniqueKey() != null) {
            if (! dataHandlers.containsKey(dataHandler.getUniqueKey())) {
                dataHandlers.put(dataHandler.getUniqueKey(), dataHandler);
                return dataHandler;
            } else {
                return (DataHandler<T>) dataHandlers.get(dataHandler.getUniqueKey());
            }
        } else {
            final PropertyChangeListener uniqueKeyListener = new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (dataHandler.getUniqueKey() != null) {
                        if (!containsDataHandlerForUniqueKey(dataHandler.getUniqueKey())) {
                            dataHandlers.put(dataHandler.getUniqueKey(), dataHandler);
                        }
                        dataHandler.removePropertyChangeListener(DataHandler.UNIQUE_KEY_PROPERTY_NAME, this);
                    }
                }
            };
            dataHandler.addPropertyChangeListener(DataHandler.UNIQUE_KEY_PROPERTY_NAME, uniqueKeyListener);
            return dataHandler;
        }
    }

    /**
     * Unregisters a {@link DataHandler}.
     *
     * @param dataHandler the data handler to unregister
     */
    public void unregisterDataHandler(DataHandler<?> dataHandler) {
        dataHandlers.remove(dataHandler.getUniqueKey());
        closeDataHandler(dataHandler);
    }

    /**
     * Checks if there is already a registered {@link DataHandler} for the provided unique key.
     *
     * @param uniqueKey the unique key
     * @return true, if there is already a registered data handler for the provided unique key, else false
     */
    public boolean containsDataHandlerForUniqueKey(Object uniqueKey) {
        return dataHandlers.containsKey(uniqueKey);
    }

    /**
     * Gets the {@link DataHandler} for the provided unique key.
     *
     * @param uniqueKey the data handler for the provided unique key
     * @return the data handler for the provided unique key, if there is any, else null
     */
    public DataHandler<?> getDataHandler(Object uniqueKey) {
        return dataHandlers.get(uniqueKey);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void close() {
        HashMap<Object, DataHandler<?>> dataHandlersCopy = new HashMap<>(dataHandlers);
        dataHandlersCopy.entrySet().stream()
                .map(Map.Entry::getValue)
                .forEach(this::closeDataHandler);
        dataHandlers.clear();
    }

    private void closeDataHandler(UniqueKeyProvider<?> dataHandler) {
        if (dataHandler instanceof AutoCloseable) {
            AutoCloseable autoCloseable = (AutoCloseable) dataHandler;
            try {
                autoCloseable.close();
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

}
