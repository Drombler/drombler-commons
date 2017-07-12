package org.drombler.commons.fx.scene.control.impl.skin;

import javafx.beans.binding.Bindings;
import javafx.scene.control.SkinBase;
import org.drombler.commons.fx.scene.control.StatusBar;

/**
 *
 * @author puce
 */
public class StatusBarSkin extends SkinBase<StatusBar> {
    private final StatusBarContentPane contentPane = new StatusBarContentPane();

    public StatusBarSkin(StatusBar control) {
        super(control);
        getChildren().add(contentPane);
        Bindings.bindContent(contentPane.getLeftPane().getChildren(), getSkinnable().getLeftEntries());
        Bindings.bindContent(contentPane.getCenterPane().getChildren(), getSkinnable().getCenterEntries());
        Bindings.bindContent(contentPane.getRightPane().getChildren(), getSkinnable().getRightEntries());
    }

}
