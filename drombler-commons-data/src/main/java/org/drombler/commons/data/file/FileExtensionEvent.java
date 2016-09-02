package org.drombler.commons.data.file;

import java.util.EventObject;

/**
 *
 * @author puce
 */
public class FileExtensionEvent extends EventObject {

    private static final long serialVersionUID = -7447499544248746089L;

    private final FileExtensionDescriptor fileExtensionDescriptor;

    public FileExtensionEvent(FileExtensionDescriptorRegistry source, FileExtensionDescriptor fileExtensionDescriptor) {
        super(source);
        this.fileExtensionDescriptor = fileExtensionDescriptor;
    }

    /**
     * @return the fileExtensionDescriptor
     */
    public FileExtensionDescriptor getFileExtensionDescriptor() {
        return fileExtensionDescriptor;
    }

}
