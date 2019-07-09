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
package org.drombler.commons.data.file;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.ContextInjector;
import org.drombler.commons.context.ContextManager;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.data.DataHandler;
import org.drombler.commons.data.DataHandlerRegistry;
import org.drombler.commons.data.Openable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.nio.file.PathUtils;
import org.softsmithy.lib.util.CloseEvent;
import org.softsmithy.lib.util.CloseEventListener;

/**
 * A utility class for files.
 *
 * @author puce
 */
public class FileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Opens a file.
     *
     * This method looks first in the {@link DataHandlerRegistry} if there is already a registered {@link DataHandler} for the specified file path and uses it if available. <br><br>
     * If the DataHandlerRegistry does not contain an according DataHandler, this method looks in the {@link FileExtensionDescriptorRegistry} if there is any registered {@link FileExtensionDescriptor}
     * for the file extension of the file to open. If one is registered this method then looks looks in the {@link DocumentHandlerDescriptorRegistry} if there is any {@link DocumentHandlerDescriptor}
     * for the associated MIME type. It then tries to create a DataHandler for the specified file path and register it in the DataHandlerRegistry. <br><br>
     * Once it has found or created a DataHandler it looks if there's an {@link Openable} registered in the local {@link Context} of the DataHandler. If it finds an Openable it calls
     * {@link Openable#open()}.
     *
     * @param fileToOpen the path to the file to open.
     * @param dataHandlerRegistry the data handler registry
     * @param fileExtensionDescriptorRegistry the file extension descriptor registry
     * @param documentHandlerDescriptorRegistry the document handler descriptor registry
     * @param contextManager the context manager
     * @param contextInjector the context injector
     * @see Openable#open()
     */
    public static void openFile(Path fileToOpen, DataHandlerRegistry dataHandlerRegistry, FileExtensionDescriptorRegistry fileExtensionDescriptorRegistry,
            DocumentHandlerDescriptorRegistry documentHandlerDescriptorRegistry, ContextManager contextManager, ContextInjector contextInjector) {
        LOG.debug("Start opening file {}...", fileToOpen);
        Object documentHandler = getDocumentHandler(fileToOpen, dataHandlerRegistry, fileExtensionDescriptorRegistry, documentHandlerDescriptorRegistry, contextManager, contextInjector);
        if (documentHandler != null
                && (!(documentHandler instanceof DataHandler) || ((DataHandler<?>) documentHandler).isInitialized())) {
            openDocument(documentHandler);
        }
    }

    private static Object getDocumentHandler(Path fileToOpen, DataHandlerRegistry dataHandlerRegistry, FileExtensionDescriptorRegistry fileExtensionDescriptorRegistry,
            DocumentHandlerDescriptorRegistry documentHandlerDescriptorRegistry, ContextManager contextManager, ContextInjector contextInjector) {
        if (dataHandlerRegistry.containsDataHandlerForUniqueKey(fileToOpen)) {
            return dataHandlerRegistry.getDataHandler(fileToOpen);
        } else {
            Object documentHandler = createNewDocumentHandler(fileToOpen, fileExtensionDescriptorRegistry, documentHandlerDescriptorRegistry);
            if (documentHandler != null && documentHandler instanceof DataHandler) {
                configureDataHandler((DataHandler<?>) documentHandler, contextManager, contextInjector, dataHandlerRegistry);
            }
            return documentHandler;
        }
    }

    private static void configureDataHandler(DataHandler<?> dataHandler, ContextManager contextManager, ContextInjector contextInjector, DataHandlerRegistry dataHandlerRegistry) {
        Contexts.configureObject(dataHandler, contextManager, contextInjector);
        if (dataHandler.isInitialized()) {
            dataHandlerRegistry.registerDataHandler(dataHandler);
        } else {
            dataHandler.addPropertyChangeListener(DataHandler.INITIALIZED_PROPERTY_NAME, new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if (dataHandler.isInitialized()) {
                        dataHandlerRegistry.registerDataHandler(dataHandler);
                        openDocument(dataHandler);
                        dataHandler.removePropertyChangeListener(DataHandler.INITIALIZED_PROPERTY_NAME, this);
                    }
                }
            });
        }
        dataHandler.addCloseEventListener(new CloseEventListener() {
            @Override
            public void onClose(CloseEvent evt) {
                dataHandler.removeCloseEventListener(this);
                dataHandlerRegistry.unregisterDataHandler(dataHandler);
                contextManager.unregisterLocalContext(dataHandler);
            }
        });
    }

    private static Object createNewDocumentHandler(Path fileToOpen, FileExtensionDescriptorRegistry fileExtensionDescriptorRegistry, DocumentHandlerDescriptorRegistry documentHandlerDescriptorRegistry) {
        String extension = PathUtils.getExtension(fileToOpen);
        FileExtensionDescriptor fileExtensionDescriptor = fileExtensionDescriptorRegistry.getFileExtensionDescriptor(extension);
        if (fileExtensionDescriptor != null) {
            String mimeType = fileExtensionDescriptor.getMimeType();
            DocumentHandlerDescriptor<?> documentHandlerDescriptor = documentHandlerDescriptorRegistry.getDocumentHandlerDescriptor(mimeType);
            if (documentHandlerDescriptor != null) {
                try {
                    return documentHandlerDescriptor.createDocumentHandler(fileToOpen);
                } catch (IllegalAccessException | SecurityException | InvocationTargetException | InstantiationException | IllegalArgumentException | NoSuchMethodException ex) {
                    LOG.error("Could not create a document handler for " + fileToOpen + "!", ex);
                }
            } else {
                LOG.warn("No DocumentHandlerDescriptor found for:" + mimeType + "!");
            }
        } else {
            LOG.warn("No FileExtensionDescriptor found for:" + extension + "!");
        }
        return null;
    }

    private static void openDocument(Object documentHandler) {
        Openable openable = Contexts.find(documentHandler, Openable.class);
        if (openable != null) {
            openable.open(); // TODO: load them in background
        } else {
            LOG.warn("No Openable found for " + documentHandler + "! "
                    + "The document handler either does not implement LocalContextProvider or does not observe registered DataCapabilityProvider.");
        }
    }

}
