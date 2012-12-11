/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.common.security.auth.message.module.srp.server;

import java.math.BigInteger;

/**
 * Challenge???
 * @author puce
 */
public class SRPChallenge {
    //B
    private BigInteger ephemeralServerPublicKey;
    
    //u
    private BigInteger randomMessage;

    SRPChallenge(BigInteger ephemeralServerPublicKey) {
        this.ephemeralServerPublicKey = ephemeralServerPublicKey;
    }
    
}
