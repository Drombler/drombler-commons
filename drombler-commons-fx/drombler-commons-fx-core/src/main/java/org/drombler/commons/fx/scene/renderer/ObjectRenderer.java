/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (Sourceforge.net user: puce).
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.renderer;

/**
 * A {@link DataRenderer} implementation which uses a {@link Object#toString()}
 * to get a text representation of the item to render. This renderer provides no
 * graphical representations of items and no style classes by default.
 *
 * @author puce
 */
public class ObjectRenderer extends AbstractDataRenderer<Object> {

    /**
     * Gets a text representation of the item.
     *
     * @see Object#toString()
     * @param item the item to render
     * @return a text representation of the item
     */
    @Override
    public String getText(Object item) {
        if (item != null) {
            return item.toString();
        } else {
            return null;
        }
    }
}
