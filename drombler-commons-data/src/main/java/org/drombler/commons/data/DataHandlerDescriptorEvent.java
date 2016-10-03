/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.data;

import java.util.EventObject;

/**
 *
 * @author puce
 * @param <D> the type of the data handler
 */
public class DataHandlerDescriptorEvent<D> extends EventObject {

    private static final long serialVersionUID = -2640012533238693088L;

    private final AbstractDataHandlerDescriptor<D> dataHandlerDescriptor;

    public DataHandlerDescriptorEvent(DataHandlerDescriptorRegistry source, AbstractDataHandlerDescriptor<D> dataHandlerDescriptor1) {
        super(source);
        this.dataHandlerDescriptor = dataHandlerDescriptor1;
    }

    /**
     * @return the dataHandlerDescriptor
     */
    public AbstractDataHandlerDescriptor<D> getDataHandlerDescriptor() {
        return dataHandlerDescriptor;
    }

}
