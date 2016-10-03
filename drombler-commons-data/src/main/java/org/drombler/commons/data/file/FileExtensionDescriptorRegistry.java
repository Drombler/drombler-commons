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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.softsmithy.lib.util.SetChangeEvent;
import org.softsmithy.lib.util.SetChangeListener;

/**
 * A registry for {@link FileExtensionDescriptor}s.
 *
 * @author puce
 */
public class FileExtensionDescriptorRegistry {

    private final Map<String, FileExtensionDescriptor> fileExtensions = new HashMap<>();
    private final Set<FileExtensionDescriptor> fileExtensionDescriptors = new HashSet<>();
    private final Set<SetChangeListener<FileExtensionDescriptor>> listeners = new HashSet<>();

    /**
     * Registers a {@link FileExtensionDescriptor}.
     *
     * @param fileExtensionDescriptor a file extension descriptor
     */
    public void registerFileExtensionDescriptor(FileExtensionDescriptor fileExtensionDescriptor) {
        fileExtensionDescriptor.getFileExtensions().stream()
                .map(String::toLowerCase)
                .forEach(fileExtension -> fileExtensions.put(fileExtension, fileExtensionDescriptor));
        fileExtensionDescriptors.add(fileExtensionDescriptor);
        fireFileExtensionAdded(fileExtensionDescriptor);
    }

    /**
     * Unregisters a {@link FileExtensionDescriptor}.
     *
     * @param fileExtensionDescriptor a file extension descriptor
     */
    public void unregisterFileExtensionDescriptor(FileExtensionDescriptor fileExtensionDescriptor) {
        fileExtensionDescriptor.getFileExtensions().stream()
                .map(String::toLowerCase)
                .forEach(fileExtensions::remove);
        fileExtensionDescriptors.remove(fileExtensionDescriptor);
        fireFileExtensionRemoved(fileExtensionDescriptor);
    }

    /**
     * Gets a registered file extension descriptor for the specified file extension.
     *
     * @param fileExtension a file extension
     * @return a registered file extension descriptor for the specified file extension
     */
    public FileExtensionDescriptor getFileExtensionDescriptor(String fileExtension) {
        return fileExtensions.get(fileExtension.toLowerCase());
    }

    /**
     * Adds a file extension listener.
     *
     * @param listener a listener
     */
    public void addFileExtensionListener(SetChangeListener<FileExtensionDescriptor> listener) {
        listeners.add(listener);
    }

    /**
     * Removes a file extension listener.
     *
     * @param listener a listener
     */
    public void removeFileExtensionListener(SetChangeListener<FileExtensionDescriptor> listener) {
        listeners.remove(listener);
    }

    /**
     * Gets all registered file extension descriptors.
     *
     * @return all registered file extension descriptors
     */
    public Set<FileExtensionDescriptor> getAllFileExtensionDescriptors() {
        return Collections.unmodifiableSet(fileExtensionDescriptors);
    }

    private void fireFileExtensionAdded(FileExtensionDescriptor fileExtensionDescriptor) {
        SetChangeEvent<FileExtensionDescriptor> event = new SetChangeEvent<>(fileExtensionDescriptors, fileExtensionDescriptor);
        listeners.forEach(listener -> listener.elementAdded(event));
    }

    private void fireFileExtensionRemoved(FileExtensionDescriptor fileExtensionDescriptor) {
        SetChangeEvent<FileExtensionDescriptor> event = new SetChangeEvent<>(fileExtensionDescriptors, fileExtensionDescriptor);
        listeners.forEach(listener -> listener.elementRemoved(event));
    }

}
