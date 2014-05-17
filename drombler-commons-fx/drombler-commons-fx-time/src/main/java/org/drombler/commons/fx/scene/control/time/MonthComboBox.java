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
package org.drombler.commons.fx.scene.control.time;

import java.time.Month;
import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import org.drombler.commons.fx.scene.control.RenderedListCellFactory;
import org.drombler.commons.fx.scene.renderer.time.MonthRenderer;

/**
 * A {@link ComboBox} for {@link Month}.
 *
 * @author puce
 */
public class MonthComboBox extends ComboBox<Month> {

    /**
     * Creates a new instance of this class.
     */
    public MonthComboBox() {
        super(FXCollections.observableList(Arrays.asList(Month.values())));
        setVisibleRowCount(Month.values().length);
        final RenderedListCellFactory<Month> cellFactory = new RenderedListCellFactory<>(new MonthRenderer());
        setButtonCell(cellFactory.call(null));
        setCellFactory(cellFactory);
    }
}
