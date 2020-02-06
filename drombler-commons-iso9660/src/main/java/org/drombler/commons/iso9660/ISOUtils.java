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
package org.drombler.commons.iso9660;

import java.nio.ByteBuffer;

/**
 *
 * @author puce
 */
public final class ISOUtils {

    private static final short MAX_UNSIGNED_BYTE = 0xFF;
//    private static final long MAX_UNSIGNED_INT32 = 0xFFFFFFFF;
    private static final int NUM_LONG_BITS = 8;
    private static final int NUM_INTEGER_BITS = 4;
    private static final int NUM_SHORT_BITS = 2;

    private ISOUtils() {
    }

    private static String getStringA(ByteBuffer byteBuffer, int length) {
        byte[] dst = getBytes(byteBuffer, length);
        return new String(dst);
    }

    private static byte[] getBytes(ByteBuffer byteBuffer, int length) {
        byte[] dst = new byte[length];
        byteBuffer.get(dst);
        return dst;
    }

    public static String getStringATrimmed(ByteBuffer byteBuffer, int length) {
        return getStringA(byteBuffer, length).trim();
    }

    private static String getStringD(ByteBuffer byteBuffer, int length) {
        return getStringA(byteBuffer, length);
    }

    public static String getStringDTrimmed(ByteBuffer byteBuffer, int length) {
        return getStringD(byteBuffer, length).trim();
    }

    public static long getUnsignedInt32LSBMSB(ByteBuffer byteBuffer) {
        byte[] bytes = getBytes(byteBuffer, 2 * NUM_INTEGER_BITS);
        long value = 0;
        for (int i = 0; i < NUM_INTEGER_BITS; i++) {
            value += getUnsignedByte(bytes[i]) << (NUM_LONG_BITS * i);
        }
        return value;
    }

    public static int getUnsignedInt16LSBMSB(ByteBuffer byteBuffer) {
        byte[] bytes = getBytes(byteBuffer, 2 * NUM_SHORT_BITS);
        int value = 0;
        for (int i = 0; i < NUM_SHORT_BITS; i++) {
            value += getUnsignedByte(bytes[i]) << (NUM_INTEGER_BITS * i);
        }
        return value;
    }

    public static short getUnsignedByte(byte value) {
        return (short) (value & MAX_UNSIGNED_BYTE);
    }
}
