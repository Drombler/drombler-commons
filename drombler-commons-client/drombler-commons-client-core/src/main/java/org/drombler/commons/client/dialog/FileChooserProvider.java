package org.drombler.commons.client.dialog;

import java.nio.file.Path;
import java.util.List;

/**
 * Provides a file chooser.
 *
 * @author puce
 */
public interface FileChooserProvider {

    /**
     * Shows a file chooser dialog to select multiple files to open.
     *
     * @return the paths of the selected files or null if no files have been selected
     */
    List<Path> showOpenMultipleDialog();

    /**
     * Shows a file chooser dialog to select a file to open.
     *
     * @return the path of the selected file or null if no file has been selected
     */
    Path showOpenDialog();

    /**
     * Shows a file chooser dialog to choose a path to save a file.
     *
     * @param initialFileName the inital final name proposed for new files
     * @return the path of the saved file or null if no file has been selected
     */
    Path showSaveAsDialog(String initialFileName);
}
