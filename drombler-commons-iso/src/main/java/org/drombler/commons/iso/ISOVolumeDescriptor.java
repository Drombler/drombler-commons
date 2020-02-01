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
import java.util.Arrays;

/**
 *
 * @author puce
 */
public abstract class ISOVolumeDescriptor {

    public static final int SECTION_LENGTH = 2048;
    private static final String IDENTIFIER = "CD001";
    private static final short VERSION = 0x01;

    private final ISOVolumeDescriptorType type;
    private final String identifier;
    private final short version;

    protected ISOVolumeDescriptor(ISOVolumeDescriptorType type, ByteBuffer byteBuffer) {
        this.type = type;
System.out.println("Type Code: " + type);
        this.identifier = ISOUtils.getStringATrimmed(byteBuffer, 5);
        if (!identifier.equals(IDENTIFIER)) {
            throw new IllegalArgumentException(
                    "The identifier must be " + IDENTIFIER + " but was: " + identifier);
        }
System.out.println("Identifier: " + identifier);
        this.version = ISOUtils.getUnsignedByte(byteBuffer.get());
        if (version != VERSION) {
            throw new IllegalArgumentException(
                    "The identifier must be " + VERSION + " but was: " + version);
        }
        System.out.println("Version: "+version);
    }

    public static ISOVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer) {
        if (byteBuffer.limit() != SECTION_LENGTH) {
            throw new IllegalArgumentException(
                    "The byteBuffer length must be " + SECTION_LENGTH + " but was: " + byteBuffer.limit());
        }
        System.out.println(byteBuffer.toString());
        System.out.println(Arrays.toString(byteBuffer.array()));
        System.out.println("Position: " + byteBuffer.position());
        ISOVolumeDescriptorType type = ISOVolumeDescriptorType.getType(ISOUtils.getUnsignedByte(byteBuffer.get()));
        System.out.println(type);
        return type.createISOVolumeDescriptor(byteBuffer);
    }
}
