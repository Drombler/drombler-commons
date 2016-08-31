package org.drombler.commons.data.file;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author puce
 */
public class SimpleFileExtensionDescriptorRegistry implements FileExtensionDescriptorRegistry {

    private final Map<String, FileExtensionDescriptor> fileExtensions = new HashMap<>();
    private final Set<FileExtensionDescriptor> fileExtensionDescriptors = new HashSet<>();
    private final Set<FileExtensionListener> listeners = new HashSet<>();

    @Override
    public void registerFileExtensionDescriptor(FileExtensionDescriptor fileExtensionDescriptor) {
        fileExtensionDescriptor.getFileExtensions().stream()
                .map(String::toLowerCase)
                .forEach(fileExtension -> fileExtensions.put(fileExtension, fileExtensionDescriptor));
        fileExtensionDescriptors.add(fileExtensionDescriptor);
        fireFileExtensionAdded(fileExtensionDescriptor);
    }

    @Override
    public void unregisterFileExtensionDescriptor(FileExtensionDescriptor fileExtensionDescriptor) {
        fileExtensionDescriptor.getFileExtensions().stream()
                .map(String::toLowerCase)
                .forEach(fileExtensions::remove);
        fileExtensionDescriptors.remove(fileExtensionDescriptor);
        fireFileExtensionRemoved(fileExtensionDescriptor);
    }

    @Override
    public FileExtensionDescriptor getFileExtensionDescriptor(String fileExtension) {
        return fileExtensions.get(fileExtension.toLowerCase());
    }

    @Override
    public void registerFileExtensionListener(FileExtensionListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterFileExtensionListener(FileExtensionListener listener) {
        listeners.remove(listener);
    }

    @Override
    public Set<FileExtensionDescriptor> getAllFileExtensionDescriptors() {
        return Collections.unmodifiableSet(fileExtensionDescriptors);
    }

    private void fireFileExtensionAdded(FileExtensionDescriptor fileExtensionDescriptor) {
        FileExtensionEvent event = new FileExtensionEvent(this, fileExtensionDescriptor);
        listeners.forEach(listener -> listener.fileExtensionAdded(event));
    }

    private void fireFileExtensionRemoved(FileExtensionDescriptor fileExtensionDescriptor) {
        FileExtensionEvent event = new FileExtensionEvent(this, fileExtensionDescriptor);
        listeners.forEach(listener -> listener.fileExtensionRemoved(event));
    }
}
