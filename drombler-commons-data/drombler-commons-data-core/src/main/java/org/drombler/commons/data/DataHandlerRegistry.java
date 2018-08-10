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

    public void registerDataHandler(DataHandler<?> dataHandler) {
        if (dataHandler.getUniqueKey() != null) {
            dataHandlers.put(dataHandler.getUniqueKey(), dataHandler);
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
        }
    }

    public void unregisterDataHandler(DataHandler<?> dataHandler) {
        dataHandlers.remove(dataHandler.getUniqueKey());
        closeDataHandler(dataHandler);
    }

    public boolean containsDataHandlerForUniqueKey(Object uniqueKey) {
        return dataHandlers.containsKey(uniqueKey);
    }

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
