package org.drombler.commons.data.file;

import java.util.Set;

public interface FileExtensionDescriptorRegistry {

    void registerFileExtensionDescriptor(FileExtensionDescriptor fileExtensionDescriptor);

    void unregisterFileExtensionDescriptor(FileExtensionDescriptor fileExtensionDescriptor);

    FileExtensionDescriptor getFileExtensionDescriptor(String fileExtension);

    Set<FileExtensionDescriptor> getAllFileExtensionDescriptors();

    void registerFileExtensionListener(FileExtensionListener listener);

    void unregisterFileExtensionListener(FileExtensionListener listener);

}
