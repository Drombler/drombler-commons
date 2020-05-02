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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.util;

/**
 * A utility class to work with mnemonics.
 *
 * @author puce
 */
public final class MnemonicUtils {

    /**
     * The character which marks the following character as mnemonic character.
     */
    public static final String MNEMONIC_CHAR = "_";

    private MnemonicUtils() {
    }

    /**
     * Removes the mnemonic marker from a display string.
     *
     * @param displayString the display string
     * @return the display string without mnemonic marker character.
     */
    public static String removeMnemonicChar(String displayString) {
        return displayString.replace(MNEMONIC_CHAR, "");
    }
}
