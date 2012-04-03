/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.lib.javafx.scene.renderer;

import java.util.Locale;
import org.softsmithy.lib.text.Localizable;

/**
 *
 * @author puce
 */
class LocalizableRenderer<T extends Localizable> extends AbstractDataRenderer<T> {


    @Override
    public String getText(T item) {
        String localizedString = null;
        if (item != null) {
            localizedString = item.getDisplayString(Locale.getDefault()); // TODO: Locale.getDefault ok? Should the locale parameter be removed from getDisplayString()?
        }
        return localizedString;
    }
}
