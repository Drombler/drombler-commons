/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.drombler.commons.fx.scene.renderer;

import java.util.Locale;
import javafx.scene.text.TextAlignment;
import org.softsmithy.lib.text.Localizer;

/**
 *
 * @author puce
 */
public class LocalizerRenderer<T> extends AbstractDataRenderer<T> {

    private final Localizer<? super T> localizer;

    public LocalizerRenderer(Localizer<? super T> localizer) {
        this.localizer = localizer;
    }

    @Override
    public String getText(T item) {
        String localizedString = null;
        if (item != null) {
            localizedString = localizer.getDisplayString(item, Locale.getDefault()); // TODO: Locale.getDefault ok? Should the locale parameter be removed from getDisplayString()?
        }
        return localizedString;
    }
}
