package org.drombler.commons.iso9660;

import java.nio.ByteBuffer;

/**
 *
 * @author Florian
 */


public class ISOPathTable {

    private final short directoryIdentifierLength;
    private final short extendedAttributeRecordLength;

    public ISOPathTable(ByteBuffer byteBuffer) {
        this.directoryIdentifierLength = ISOUtils.getUnsignedByte(byteBuffer);
        this.extendedAttributeRecordLength = ISOUtils.getUnsignedByte(byteBuffer);
    }
    
}
