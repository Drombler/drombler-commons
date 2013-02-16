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
package org.drombler.commons.fx.scene.renderer.time.calendar;

import java.util.Locale;
import javax.time.calendar.MonthOfYear;
import org.drombler.commons.fx.scene.renderer.AbstractDataRenderer;

/**
 *
 * @author puce
 */
public class MonthOfYearRenderer extends AbstractDataRenderer<MonthOfYear> {

    private final boolean shortText;

    public MonthOfYearRenderer() {
        this(false);
    }

    public MonthOfYearRenderer(boolean shortText) {
        this.shortText = shortText;
    }

    @Override
    public String getText(MonthOfYear moy) {
        if (moy != null) {
            if (shortText) {
                return moy.getShortText(Locale.getDefault());
            } else {
                return moy.getText(Locale.getDefault());
            }
        } else {
            return null;
        }
    }
}
