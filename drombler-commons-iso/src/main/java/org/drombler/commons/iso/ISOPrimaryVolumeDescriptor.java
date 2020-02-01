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
public class ISOPrimaryVolumeDescriptor extends ISOVolumeDescriptor {

    private static final int SYSTEM_IDENTIFIER_LENGTH = 32;
    private static final int VOLUME_IDENTIFIER_LENGTH = 32;
    private final String systemIdentifier;
    private final String volumeIdentifier;
    private final long volumeSpaceSize;
    private final int volumeSetSize;
    private final int volumeSequenceNumber;
    private final int logicalBlockSize;
    private final long pathTableSize;

    public ISOPrimaryVolumeDescriptor(ByteBuffer byteBuffer) {
        super(ISOVolumeDescriptorType.PRIMARY_VOLUME_DESCRIPTOR, byteBuffer);
        readUnused(byteBuffer, 1);
        systemIdentifier = ISOUtils.getStringATrimmed(byteBuffer, SYSTEM_IDENTIFIER_LENGTH);
        System.out.println("System Identifier: " +systemIdentifier);
        volumeIdentifier = ISOUtils.getStringDTrimmed(byteBuffer, VOLUME_IDENTIFIER_LENGTH);
        System.out.println("Volume Identifier: "+ volumeIdentifier);
        readUnused(byteBuffer, 8);
        volumeSpaceSize = ISOUtils.getUnsignedInt32LSBMSB(byteBuffer);
        System.out.println("Volume Space Size: "+volumeSpaceSize);
        readUnused(byteBuffer, 32);
        volumeSetSize = ISOUtils.getUnsignedInt16LSBMSB(byteBuffer);
        System.out.println("Volume Set Size: "+volumeSetSize);
        volumeSequenceNumber = ISOUtils.getUnsignedInt16LSBMSB(byteBuffer);
        System.out.println("Volume Sequence Number: "+volumeSequenceNumber);
        logicalBlockSize  = ISOUtils.getUnsignedInt16LSBMSB(byteBuffer);
        System.out.println("Logical Block Size: "+logicalBlockSize);
        pathTableSize = ISOUtils.getUnsignedInt32LSBMSB(byteBuffer);
        System.out.println("Path Table Size : "+pathTableSize);
    }

    private void readUnused(ByteBuffer byteBuffer, int length) {
        byte[] dst = new byte[length];
        byteBuffer.get(dst);

        for (byte unused : dst) {
            if (unused != 0) {
                throw new IllegalArgumentException("0s expected but was: " + Arrays.toString(dst));
            }
        }
    }

}
