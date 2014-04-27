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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.text.FormatException;
import org.softsmithy.lib.text.Formatter;

/**
 * A {@link DataRenderer} implementation which uses a {@link Formatter} to get a
 * text representation of the item to render. This renderer provides no
 * graphical representations of items and no style classes by default.
 *
 * @param <T> the type of the data to render
 * @author puce
 */
public class FormatterDataRenderer<T> extends AbstractDataRenderer<T> {

    private static final Logger LOG = LoggerFactory.getLogger(FormatterDataRenderer.class);

    private final Formatter<? super T> formatter;

    /**
     * Creates a new instance of this class.
     *
     * @param formatter a {@link Formatter} to get a text representation for the
     * items to render.
     */
    public FormatterDataRenderer(Formatter<? super T> formatter) {
        this.formatter = formatter;
    }

    /**
     * Gets a text representation of the item.
     *
     * @see Formatter#format(java.lang.Object)
     * @param item the item to render
     * @return a text representation of the item
     */
    @Override
    public String getText(T item) {
        if (item != null) {
            try {
                return formatter.format(item);
            } catch (FormatException ex) {
                // TODO: good enough just to log?
                LOG.error(ex.getMessage(), ex);
                return null;
            }
        } else {
            return null;
        }

    }
}
