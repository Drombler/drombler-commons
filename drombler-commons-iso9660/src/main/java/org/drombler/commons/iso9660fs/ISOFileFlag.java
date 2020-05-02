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
package org.drombler.commons.iso9660fs;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author puce
 */
public enum ISOFileFlag {

    HIDDEN((byte) 1),
    DIRECTORY((byte) 2),
    ASSOCIATED_FILE((byte) 4),
    SEE_EXTENDED_ATTRIBUTE_RECORD((byte) 8),
    OWNER_GROUP_PERMISSIONS((byte) 16),
    RESERVED1((byte) 32),
    RESERVED2((byte) 64),
    NOT_FINAL_DIRECTORY_RECORD((byte) 128);

    private static final Map<Byte, ISOFileFlag> TYPES = new HashMap<>();

    static {
        for (ISOFileFlag flag : values()) {
            TYPES.put(flag.bit, flag);
        }
    }

    private final byte bit;

    private ISOFileFlag(byte bit) {
        this.bit = bit;
    }

    public static ISOFileFlag getFlag(byte bit) {
        return TYPES.get(bit);
    }

    public static Set<ISOFileFlag> convertBitSet(byte bitMask) {
        Set<ISOFileFlag> fileFlags = EnumSet.noneOf(ISOFileFlag.class);
        for (ISOFileFlag flag : values()) {
            if ((bitMask & flag.bit) == flag.bit) {
                fileFlags.add(flag);
            }
        }
        return fileFlags;
    }
}
