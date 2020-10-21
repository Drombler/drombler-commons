package org.drombler.commons.fx.samples.scene.control.treeview.path;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCombination;
import javafx.stage.DirectoryChooser;
import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.client.util.MnemonicUtils;

/**
 *
 * @author puce
 */
public class ChooseDirectoryAction extends AbstractFXAction {

    private final DirectoryChooser directoryChooser;

    private final SelectedDirectoryPathProperty selectedDirectoryPath = new SelectedDirectoryPathProperty();

    public ChooseDirectoryAction() {
        setDisplayName("Choose _Directory...");
        setAccelerator(KeyCombination.keyCombination("Shortcut+D"));
        this.directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(MnemonicUtils.removeMnemonicChar(getDisplayName()));
    }

    @Override
    public void handle(ActionEvent event) {
        File directory = directoryChooser.showDialog(null);
        if (directory != null) {
            Path directoryPath = directory.toPath();
            Path initialDirectoryPath = directoryPath.getParent() != null ? directoryPath.getParent() : directoryPath;
            directoryChooser.setInitialDirectory(initialDirectoryPath.toFile());
            selectedDirectoryPath.set(directoryPath);
        }
    }

    public final Path getSelectedDirectoryPath() {
        return selectedDirectoryPathProperty().get();
    }

    public ReadOnlyObjectProperty<Path> selectedDirectoryPathProperty() {
        return selectedDirectoryPath;
    }

    private class SelectedDirectoryPathProperty extends ReadOnlyObjectPropertyBase<Path> {

        private Path selectedDirectoryPath = null;

        @Override
        public final Path get() {
            return selectedDirectoryPath;
        }

        private void set(Path newValue) {
            if (!Objects.equals(selectedDirectoryPath, newValue)) {
                selectedDirectoryPath = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return ChooseDirectoryAction.this;
        }

        @Override
        public String getName() {
            return "selectedDirectoryPath";
        }
    }
}
