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
package org.drombler.commons.client.startup.main;

/**
 * A missing property exception.
 */
public class MissingPropertyException extends Exception {

    private static final long serialVersionUID = 6316141167207690543L;

    /**
     * Creates a new instance of this class.
     */
    public MissingPropertyException() {
    }

    /**
     * Creates a new instance of this class.
     *
     * @param message the message
     */
    public MissingPropertyException(String message) {
        super(message);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param message the message
     * @param cause the cause of this exception
     */
    public MissingPropertyException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param cause the cause of this exception
     */
    public MissingPropertyException(Throwable cause) {
        super(cause);
    }

    /**
     * Creates a new instance of this class.
     *
     * @param message the message
     * @param cause the cause of this exception
     * @param enableSuppression flag if suppression is enabled
     * @param writableStackTrace flag if the stack trace should be writable
     */
    public MissingPropertyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
