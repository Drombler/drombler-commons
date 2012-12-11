/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
