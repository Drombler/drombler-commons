package org.drombler.commons.fx.samples.scene.control.treeview.facility;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import org.drombler.commons.action.fx.context.AbstractActiveContextSensitiveFXAction;
import org.drombler.commons.context.ContextEvent;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.handler.FacilityHandler;
import org.drombler.commons.fx.samples.scene.control.treeview.facility.model.Facility;

/**
 *
 * @author puce
 */


public class AddAction extends AbstractActiveContextSensitiveFXAction<FacilityHandler<?, ?>> {

    private FacilityHandler<?, ?> facilityFoo;

    public AddAction() {
        super((Class<FacilityHandler<?, ?>>) (Class<?>) FacilityHandler.class);
        setEnabled(false);
    }

    @Override
    public void handle(ActionEvent event) {
        handleChild(facilityFoo);
    }

    private <F extends Facility, C extends Facility> void handleChild(FacilityHandler<F, C> facilityFoo) {
        FacilityHandler<C, ?> childFoo = facilityFoo.createChildHandler();
        Dialog<ButtonType> dialog = createNewFacilityDialog(childFoo);
        boolean ok = dialog.showAndWait()
                .filter(result -> result == ButtonType.OK)
                .isPresent();

        if (ok) {
            facilityFoo.addChild(childFoo.getFacility());
        }
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

    @Override
    protected void contextChanged(ContextEvent<FacilityHandler<?, ?>> event) {
        facilityFoo = getActiveContext().find(event.getType());
        setEnabled(facilityFoo != null && facilityFoo.isSupportingChildren());
    }

}
