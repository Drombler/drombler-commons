package org.drombler.commons.iso;

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
