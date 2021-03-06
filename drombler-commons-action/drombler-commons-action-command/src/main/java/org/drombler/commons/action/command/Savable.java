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
package org.drombler.commons.action.command;

import java.util.Locale;

/**
 * An action command to save something.
 *
 * @author puce
 */
//  TODO: implement Localizable from SoftSmithy? Else the Locale parameter of getDisplayString might be omitted 
// (and the default locale used instead)
public interface Savable {

    /**
     * Executes this save command.
     */
    void save();

    /**
     * Gets the display string for this command.
     *
     * @param inLocale the locale used to display the text.
     * @return the display string for this command
     */
    String getDisplayString(Locale inLocale);
}
