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
 * Copyright 2012 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.fxml;

/**
 *
 * @author puce
 */
class FXMLLoadingException extends RuntimeException{


    public FXMLLoadingException() {
    }

    public FXMLLoadingException(String message) {
        super(message);
    }

    public FXMLLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FXMLLoadingException(Throwable cause) {
        super(cause);
    }
    
}
