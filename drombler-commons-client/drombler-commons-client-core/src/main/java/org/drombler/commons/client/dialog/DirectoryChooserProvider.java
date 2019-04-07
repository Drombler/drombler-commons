package org.drombler.commons.client.dialog;

import java.nio.file.Path;

/**
 * Provides a directory chooser.
 */
public interface DirectoryChooserProvider {

    /**
     * Shows a directory chooser dialog.
     *
     * @return the path of the selected directory or null if no directory has been selected
     */
    Path showDialog();

}
