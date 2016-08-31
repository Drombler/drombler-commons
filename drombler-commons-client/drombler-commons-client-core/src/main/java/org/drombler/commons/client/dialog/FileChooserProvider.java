package org.drombler.commons.client.dialog;

import java.nio.file.Path;
import java.util.List;

/**
 * Provides a file chooser.
 *
 * @author puce
 */
public interface FileChooserProvider {

    List<Path> showOpenMultipleDialog();

    Path showOpenDialog();

    Path showSaveAsDialog(String initialFileName);
}
