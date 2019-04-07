package org.drombler.commons.data;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    private final Map<DataHandlerRegistryKey<?, ?>, DataHandler<?>> dataHandlers = new HashMap<>();

    /**
     * Registers a data handler. If a data handler is already registered for the unique key, the registered data handler will be returned. Use the returned data handler.
     *
     * If the unique key of the data handler is non-null, the data handler is registered immediately. Else the unique key property of the data handler will be observed for changes and once it's
     * non-null, the data handler will be registerd.
     *
     * @param <T> the type of the unique key of the data handler
     * @param dataHandler the data handler
     * @return the registered data handler for the unique key or observed data handler for null unique keys
     */
    public <K, T extends DataHandler<K>> T registerDataHandler(T dataHandler) {
        final DataHandlerRegistryKey<K, T> dataHandlerRegistryKey = createDataHandlerRegistryKey(dataHandler);
        if (dataHandler.getUniqueKey() != null) {
            if (!containsDataHandlerForUniqueKey(dataHandlerRegistryKey)) {
                dataHandlers.put(dataHandlerRegistryKey, dataHandler);
                return dataHandler;
            } else {
                return getDataHandler(dataHandlerRegistryKey);
            }
        } else {
            final PropertyChangeListener uniqueKeyListener = new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (dataHandler.getUniqueKey() != null) {
                        if (!containsDataHandlerForUniqueKey(dataHandlerRegistryKey)) {
                            dataHandlers.put(dataHandlerRegistryKey, dataHandler);
                        }
                        dataHandler.removePropertyChangeListener(DataHandler.UNIQUE_KEY_PROPERTY_NAME, this);
                    }
                }
            };
            dataHandler.addPropertyChangeListener(DataHandler.UNIQUE_KEY_PROPERTY_NAME, uniqueKeyListener);
            return dataHandler;
        }
    }

    private <K, T extends DataHandler<K>> DataHandlerRegistryKey createDataHandlerRegistryKey(T dataHandler) {
        return new DataHandlerRegistryKey<>(dataHandler.getClass(), dataHandler.getUniqueKey());
    }

    /**
     * Unregisters a data handler.
     *
     * @param dataHandler a data handler
     */
    public void unregisterDataHandler(DataHandler<?> dataHandler) {
        DataHandlerRegistryKey<?, ?> dataHandlerRegistryKey = createDataHandlerRegistryKey(dataHandler);
        dataHandlers.remove(dataHandlerRegistryKey);
        closeDataHandler(dataHandler);
    }

    /**
     * Checks if a data handler is registered for the unique key
     *
     * @param uniqueKey
     * @return
     */
    public <K, T extends DataHandler<K>> boolean containsDataHandlerForUniqueKey(Class<T> type, K uniqueKey) {
        DataHandlerRegistryKey<K, T> dataHandlerRegistryKey = new DataHandlerRegistryKey<>(type, uniqueKey);
        return containsDataHandlerForUniqueKey(dataHandlerRegistryKey);
    }

    private <K, T extends DataHandler<K>> boolean containsDataHandlerForUniqueKey(DataHandlerRegistryKey<K, T> dataHandlerRegistryKey) {
        return dataHandlers.containsKey(dataHandlerRegistryKey);
    }

    /**
     * Gets the data handler for the specified unique key
     *
     * @param uniqueKey
     * @return
     */
    public <K, T extends DataHandler<K>> T getDataHandler(Class<T> type, K uniqueKey) {
        DataHandlerRegistryKey<K, T> dataHandlerRegistryKey = new DataHandlerRegistryKey<>(type, uniqueKey);
        return getDataHandler(dataHandlerRegistryKey);
    }

    private <K, T extends DataHandler<K>> T getDataHandler(DataHandlerRegistryKey<K, T> dataHandlerRegistryKey) {
        return dataHandlerRegistryKey.getType().cast(dataHandlers.get(dataHandlerRegistryKey));
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

    private static class DataHandlerRegistryKey<K, T extends DataHandler<K>> {

        private final Class<T> type;
        private final K uniqueKey;

        public DataHandlerRegistryKey(Class<T> type, K uniqueKey) {
            this.type = type;
            this.uniqueKey = uniqueKey;
        }

        /**
         * @return the type
         */
        public Class<T> getType() {
            return type;
        }

        /**
         * @return the uniqueKey
         */
        public K getUniqueKey() {
            return uniqueKey;
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 53 * hash + Objects.hashCode(this.type);
            hash = 53 * hash + Objects.hashCode(this.uniqueKey);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DataHandlerRegistryKey<?, ?>)) {
                return false;
            }
            final DataHandlerRegistryKey<?, ?> other = (DataHandlerRegistryKey<?, ?>) obj;
            return Objects.equals(this.type, other.type)
                    && Objects.equals(this.uniqueKey, other.uniqueKey);
        }

    }
}
