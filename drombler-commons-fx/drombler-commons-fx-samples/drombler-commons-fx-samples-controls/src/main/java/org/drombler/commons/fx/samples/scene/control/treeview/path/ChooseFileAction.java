package org.drombler.commons.fx.samples.scene.control.treeview.path;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectPropertyBase;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCombination;
import javafx.stage.FileChooser;
import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.client.util.MnemonicUtils;

/**
 *
 * @author puce
 */
public class ChooseFileAction extends AbstractFXAction {

    private final FileChooser fileChooser;

    private final SelectedFilePathProperty selectedFilePath = new SelectedFilePathProperty();

    public ChooseFileAction() {
        setDisplayName("Choose _File...");
        setAccelerator(KeyCombination.keyCombination("Shortcut+F"));
        this.fileChooser = new FileChooser();
        fileChooser.setTitle(MnemonicUtils.removeMnemonicChar(getDisplayName()));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("zip, jar Files", "*.zip", "*.jar"));
    }

    @Override
    public void handle(ActionEvent event) {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Path filePath = file.toPath();
            fileChooser.setInitialDirectory(filePath.getParent().toFile());
            selectedFilePath.set(filePath);
        }
    }

    public final Path getSelectedFilePath() {
        return selectedFilePathProperty().get();
    }

    public ReadOnlyObjectProperty<Path> selectedFilePathProperty() {
        return selectedFilePath;
    }

    private class SelectedFilePathProperty extends ReadOnlyObjectPropertyBase<Path> {

        private Path selectedFilePath = null;

        @Override
        public final Path get() {
            return selectedFilePath;
        }

        private void set(Path newValue) {
            if (!Objects.equals(selectedFilePath, newValue)) {
                selectedFilePath = newValue;
                fireValueChangedEvent();
            }
        }

        @Override
        public Object getBean() {
            return ChooseFileAction.this;
        }

        @Override
        public String getName() {
            return "selectedFilePath";
        }
    }
}
