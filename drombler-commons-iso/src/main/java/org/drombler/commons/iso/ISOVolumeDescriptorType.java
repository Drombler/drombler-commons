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
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author puce
 */
public enum ISOVolumeDescriptorType {

    BOOT_RECORD((short) 0) {

        @Override
        public ISOVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    },
    PRIMARY_VOLUME_DESCRIPTOR((short) 1) {

        @Override
        public ISOPrimaryVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer) {
            return new ISOPrimaryVolumeDescriptor(byteBuffer);
        }

    },
    SUPPLEMENTARY_VOLUME_DESCRIPTOR((short) 2) {

        @Override
        public ISOVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    },
    VOLUME_PARTITION_DESCRIPTOR((short) 3) {

        @Override
        public ISOVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    },
    RESERVED((short) 4) {

        @Override
        public ISOVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    },
    VOLUME_DESCRIPTOR_SET_TERMINATOR((short) 255) {

        @Override
        public ISOVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };

    private static final Map<Short, ISOVolumeDescriptorType> TYPES = new HashMap<>();

    static {
        for (ISOVolumeDescriptorType type : values()) {
            TYPES.put(type.value, type);
        }
    }

    private final short value;

    private ISOVolumeDescriptorType(short value) {
        this.value = value;
    }

    public static ISOVolumeDescriptorType getType(short value) {
        if (TYPES.containsKey(value)) {
            return TYPES.get(value);
        } else {
            return RESERVED;
        }
    }

    public abstract ISOVolumeDescriptor createISOVolumeDescriptor(ByteBuffer byteBuffer);
}
