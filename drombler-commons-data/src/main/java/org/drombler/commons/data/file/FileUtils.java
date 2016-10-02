package org.drombler.commons.data.file;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import org.drombler.commons.context.Context;
import org.drombler.commons.context.Contexts;
import org.drombler.commons.data.Openable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.nio.file.PathUtils;

/**
 * A utility class for files.
 *
 * @author puce
 */
public class FileUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtils.class);

    /**
     * Opens a file. It looks in the {@link FileExtensionDescriptorRegistry} if there is any registered {@link FileExtensionDescriptor} for the file extension of the file open. If one is registered it
     * lools in the {@link DocumentHandlerDescriptorRegistry} if there is any {@link DocumentHandlerDescriptor} for the associated MIME type. It then tries to create a Document Handler for the
     * specified file path and looks if there's an {@link Openable} registered in its local {@link Context}. If it finds an Openable it calls {@link Openable#open() }.
     *
     * @param fileToOpen the path to the file to open.
     * @param fileExtensionDescriptorRegistry the file extension descriptor registry
     * @param documentHandlerDescriptorRegistry the document handler descriptor registry
     * @see Openable#open()
     */
    public static void openFile(Path fileToOpen, FileExtensionDescriptorRegistry fileExtensionDescriptorRegistry, DocumentHandlerDescriptorRegistry documentHandlerDescriptorRegistry) {
        LOG.debug("Start opening file {}...", fileToOpen);
        String extension = PathUtils.getExtension(fileToOpen);
        FileExtensionDescriptor fileExtensionDescriptor = fileExtensionDescriptorRegistry.getFileExtensionDescriptor(extension);
        if (fileExtensionDescriptor != null) {
            String mimeType = fileExtensionDescriptor.getMimeType();
            DocumentHandlerDescriptor<?> documentHandlerDescriptor = documentHandlerDescriptorRegistry.getDocumentHandlerDescriptor(mimeType);
            if (documentHandlerDescriptor != null) {
                try {
                    Object documentHandler = documentHandlerDescriptor.createDocumentHandler(fileToOpen);
                    Openable openable = Contexts.find(documentHandler, Openable.class);
                    if (openable != null) {
                        openable.open(); // TODO: load them in background
                    } else {
                        LOG.warn("No Openable found for " + documentHandler + "! "
                                + "The document handler either does not implement LocalContextProvider or does not observe registered DataCapabilityProvider.");
                    }
                } catch (IllegalAccessException | SecurityException | InvocationTargetException | InstantiationException | IllegalArgumentException | NoSuchMethodException ex) {
                    LOG.error("Could not create a document handler for " + fileToOpen + "!", ex);
                }
            } else {
                LOG.warn("No DocumentHandlerDescriptor found for:" + mimeType + "!");
            }
        } else {
            LOG.warn("No FileExtensionDescriptor found for:" + extension + "!");
        }
    }

}
