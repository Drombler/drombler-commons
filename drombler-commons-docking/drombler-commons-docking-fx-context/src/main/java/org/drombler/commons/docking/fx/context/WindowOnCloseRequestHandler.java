package org.drombler.commons.docking.fx.context;

import java.util.ResourceBundle;
import java.util.SortedSet;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.drombler.commons.docking.context.DockingAreaContainer;
import org.drombler.commons.docking.fx.FXDockableData;
import org.drombler.commons.docking.fx.FXDockableEntry;
import org.drombler.commons.docking.fx.context.impl.SaveModifiedDockablesPane;

/**
 * A window on close request handler which handles modified Dockables on window close request.
 *
 * @see Stage#setOnCloseRequest(javafx.event.EventHandler)
 * @author puce
 */
public class WindowOnCloseRequestHandler implements EventHandler<WindowEvent> {

    private final ResourceBundle resourceBundle = ResourceBundleUtils.getClassResourceBundle(WindowOnCloseRequestHandler.class);
    private final DockingAreaContainer<Node, FXDockableData, FXDockableEntry> dockingAreaContainer;

    public WindowOnCloseRequestHandler(DockingAreaContainer<Node, FXDockableData, FXDockableEntry> dockingAreaContainer) {
        this.dockingAreaContainer = dockingAreaContainer;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void handle(WindowEvent event) {
        if (!handleModifiedDockables()) {
            event.consume();
        }
    }

    public boolean handleModifiedDockables() {
        SortedSet<FXDockableEntry> modifiedDockables = dockingAreaContainer.getSortedModifiedDockables();

        if (!modifiedDockables.isEmpty()) {
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle(resourceBundle.getString("dialog.title"));
            dialog.setResizable(true);
            SaveModifiedDockablesPane saveModifiedDockablesPane = new SaveModifiedDockablesPane(modifiedDockables);
            saveModifiedDockablesPane.emptyProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    dialog.setResult(ButtonType.OK);
                }
            });
            dialog.getDialogPane().setContent(saveModifiedDockablesPane);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
            return !dialog.showAndWait()
                    .filter(result -> result == ButtonType.CANCEL)
                    .isPresent();
        } else {
            return true;
        }
    }
}
