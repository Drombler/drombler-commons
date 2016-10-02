package org.drombler.commons.data.file;

import java.util.EventListener;

/**
 *
 * @author puce
 */
// TODO: FileExtensionRegistryListener ?
public interface FileExtensionListener extends EventListener {

    void fileExtensionAdded(FileExtensionEvent event);

    void fileExtensionRemoved(FileExtensionEvent event);
}
