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
 * Copyright 2020 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.iso9660fs;

import java.nio.ByteBuffer;
import java.time.ZonedDateTime;
import java.util.Set;

/**
 *
 * @author Florian
 */
public class ISODirectoryDescriptor {

    private final short length;
    private final short extendedAttributeRecordLength;
    private final long locationOfExtend;
    private final long dataLength;
    private final ZonedDateTime recordingDateTime;
    private final Set<ISOFileFlag> fileFlags;
    private final short interleavedModeFileUnitSize;
    private final short interleavedModeInterleaveGapSize;
    private final int volumeSequenceNumber;
    private final short fileIdentifierLength;
    private final String fileIdentifier;

    public ISODirectoryDescriptor(ByteBuffer byteBuffer) {
        this.length = ISOUtils.getUnsignedByte(byteBuffer);
        this.extendedAttributeRecordLength = ISOUtils.getUnsignedByte(byteBuffer);
        this.locationOfExtend = ISOUtils.getUnsignedInt32LSBMSB(byteBuffer);
        this.dataLength = ISOUtils.getUnsignedInt32LSBMSB(byteBuffer);
        this.recordingDateTime = ISOUtils.getDirectoryDateTime(byteBuffer);
        this.fileFlags = ISOFileFlag.convertBitSet(byteBuffer.get());
        this.interleavedModeFileUnitSize = ISOUtils.getUnsignedByte(byteBuffer);
        this.interleavedModeInterleaveGapSize = ISOUtils.getUnsignedByte(byteBuffer);
        this.volumeSequenceNumber = ISOUtils.getUnsignedInt16LSBMSB(byteBuffer);
        this.fileIdentifierLength = ISOUtils.getUnsignedByte(byteBuffer);
        this.fileIdentifier = ISOUtils.getStringDTrimmed(byteBuffer, fileIdentifierLength);
    }

    @Override
    public String toString() {
        return "ISODirectoryDescriptor{"
                + "length=" + length
                + ", extendedAttributeRecordLength=" + extendedAttributeRecordLength
                + ", locationOfExtend=" + locationOfExtend
                + ", dataLength=" + dataLength
                + ", recordingDateTime=" + recordingDateTime
                + ", fileFlags=" + fileFlags
                + ", interleavedModeFileUnitSize=" + interleavedModeFileUnitSize
                + ", interleavedModeInterleaveGapSize=" + interleavedModeInterleaveGapSize
                + ", volumeSequenceNumber=" + volumeSequenceNumber
                + ", fileIdentifierLength=" + fileIdentifierLength
                + ", fileIdentifier=" + fileIdentifier + '}';
    }

    /**
     * @return the length
     */
    public short getLength() {
        return length;
    }

    /**
     * @return the extendedAttributeRecordLength
     */
    public short getExtendedAttributeRecordLength() {
        return extendedAttributeRecordLength;
    }

    /**
     * @return the locationOfExtend
     */
    public long getLocationOfExtend() {
        return locationOfExtend;
    }

    /**
     * @return the dataLength
     */
    public long getDataLength() {
        return dataLength;
    }

    /**
     * @return the recordingDateTime
     */
    public ZonedDateTime getRecordingDateTime() {
        return recordingDateTime;
    }

    /**
     * @return the fileFlags
     */
    public Set<ISOFileFlag> getFileFlags() {
        return fileFlags;
    }

    /**
     * @return the interleavedModeFileUnitSize
     */
    public short getInterleavedModeFileUnitSize() {
        return interleavedModeFileUnitSize;
    }

    /**
     * @return the interleavedModeInterleaveGapSize
     */
    public short getInterleavedModeInterleaveGapSize() {
        return interleavedModeInterleaveGapSize;
    }

    /**
     * @return the volumeSequenceNumber
     */
    public int getVolumeSequenceNumber() {
        return volumeSequenceNumber;
    }

    /**
     * @return the fileIdentifierLength
     */
    public short getFileIdentifierLength() {
        return fileIdentifierLength;
    }

    /**
     * @return the fileIdentifier
     */
    public String getFileIdentifier() {
        return fileIdentifier;
    }

}
