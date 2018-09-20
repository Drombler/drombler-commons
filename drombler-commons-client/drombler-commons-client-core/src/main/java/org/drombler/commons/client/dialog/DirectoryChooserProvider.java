package org.drombler.commons.client.dialog;

import java.nio.file.Path;

/**
 * Provides a directory chooser.
 */
public interface DirectoryChooserProvider {

    Path showDialog();

}
