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
package org.projectx.common.security.auth.message.module.srp.server;

import java.math.BigInteger;

/**
 *
 * @author puce
 */
public class SRPCredentials {
    private String username;
    // v
    private BigInteger passwordVerifier;
    //s
    private BigInteger salt;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the passwordVerifier
     */
    public BigInteger getPasswordVerifier() {
        return passwordVerifier;
    }

    /**
     * @return the salt
     */
    public BigInteger getSalt() {
        return salt;
    }
}
