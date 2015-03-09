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
package org.drombler.commons.iso;

import java.nio.ByteBuffer;

/**
 *
 * @author puce
 */
public final class ISOUtils {

    private static final short MAX_UNSIGNED_BYTE = 0xFF;
    private ISOUtils() {
    }

    public static String getStringA(ByteBuffer byteBuffer, int length) {
        byte[] dst = new byte[length];
        byteBuffer.get(dst);
        return new String(dst);
    }

    public static short getUnsignedByte(byte value) {
        return (short) (value & MAX_UNSIGNED_BYTE);
    }
}
