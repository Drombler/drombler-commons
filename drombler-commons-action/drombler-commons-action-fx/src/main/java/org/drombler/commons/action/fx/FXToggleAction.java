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
package org.drombler.commons.action.fx;

import javafx.beans.property.BooleanProperty;

/**
 * FXToggleAction keeps the state (enabled/ disabled, selected/ unselected etc.), the information (texts, image etc.)
 * and the logic between menu items and toolbar buttons in sync.
 *
 * @author puce
 */
public interface FXToggleAction extends FXAction {

    /**
     * The selected state.
     *
     * @return the selected state property
     */
    BooleanProperty selectedProperty();

    boolean isSelected();

    void setSelected(boolean selected);
}
