package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.control.TreeItem;
import org.drombler.commons.action.fx.AbstractFXAction;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.handler.FacilityHandler;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Facility;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.tree.AbstractFacilityTreeItem;

/**
 *
 * @author puce
 */
public class AddAction extends AbstractFXAction {

    private final MultipleSelectionModel<TreeItem<Facility>> selectionModel;

    /**
     *
     * @param selectionModel
     */
    public AddAction(MultipleSelectionModel<TreeItem<Facility>> selectionModel) {
        this.selectionModel = selectionModel;
        setDisplayName("+");
        selectionModel.selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setEnabled(newValue != null
                    && ((AbstractFacilityTreeItem<?>) newValue).getFacilityHandler().isSupportingChildren());

        });
        setEnabled(false);
    }

    @Override
    public void handle(ActionEvent event) {
        handleChild(getSelectedFacilityHandler(), getSelectedFacilityHandler().getFacilityHandler());
    }

    private <F extends Facility, C extends Facility> void handleChild(AbstractFacilityTreeItem<?> facilityTreeItem, FacilityHandler<F, C> facilityHandler) {
        FacilityHandler<C, ?> childHandler = facilityHandler.createChildHandler();
        Dialog<ButtonType> dialog = createNewFacilityDialog(childHandler);
        boolean ok = dialog.showAndWait()
                .filter(result -> result == ButtonType.OK)
                .isPresent();

        if (ok) {
            facilityHandler.addChild(childHandler.getFacility());
//            selectionModel.select(facilityTreeItem.getChildren().get(facilityTreeItem.getChildren().size()));
        }
    }

    private AbstractFacilityTreeItem<?> getSelectedFacilityHandler() {
        return ((AbstractFacilityTreeItem<?>) selectionModel.getSelectedItem());
    }

    public <C extends Facility> Dialog<ButtonType> createNewFacilityDialog(FacilityHandler<C, ?> childFoo) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("New Facility");
        dialog.setResizable(true);
        Node editor = childFoo.getEditor();
        dialog.getDialogPane().setContent(editor);
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
//            ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setDefaultButton(true);
        return dialog;
    }

}
