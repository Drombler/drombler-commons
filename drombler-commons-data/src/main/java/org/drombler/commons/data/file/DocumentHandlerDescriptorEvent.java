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
package org.drombler.commons.data.file;

import java.util.EventObject;

/**
 *
 * @author puce
 */
public class DocumentHandlerDescriptorEvent<D> extends EventObject {

    private static final long serialVersionUID = 1837696044825618602L;

    private final DocumentHandlerDescriptor<D> documentHandlerDescriptor;

    public DocumentHandlerDescriptorEvent(DocumentHandlerDescriptorRegistry source, DocumentHandlerDescriptor<D> documentHandlerDescriptor) {
        super(source);
        this.documentHandlerDescriptor = documentHandlerDescriptor;
    }

    /**
     * @return the documentHandlerDescriptor
     */
    public DocumentHandlerDescriptor<D> getDocumentHandlerDescriptor() {
        return documentHandlerDescriptor;
    }

}
