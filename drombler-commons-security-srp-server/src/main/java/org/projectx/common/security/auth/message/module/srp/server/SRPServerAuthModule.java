/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.common.security.auth.message.module.srp.server;

import java.math.BigInteger;
import java.util.Map;
import java.util.Random;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.agreement.srp.SRP6Server;

/**
 *
 * @author puce
 */
public class SRPServerAuthModule implements ServerAuthModule {

    private SRP6Server srp6Server = new SRP6Server();
    // A
    private BigInteger ephemeralClientPublicKey;
    //////////////////////////////////////////////////
    private static final Class[] SUPPORTED_MESSAGE_TYPES = new Class[]{HttpServletRequest.class, 
        HttpServletResponse.class};
    private MessagePolicy requestPolicy;
    private MessagePolicy responsePolicy;
    private CallbackHandler handler;
    private Map options;

    @Override
    public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) throws AuthException {
        this.requestPolicy = requestPolicy;
        this.responsePolicy = responsePolicy;
        this.handler = handler;
        this.options = options;
    }

    @Override
    public Class[] getSupportedMessageTypes() {
       return SUPPORTED_MESSAGE_TYPES;
    }

    @Override
    public AuthStatus validateRequest(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
        return step1(messageInfo);
    }

    @Override
    public AuthStatus secureResponse(MessageInfo messageInfo, Subject serviceSubject) throws AuthException {
        return AuthStatus.SEND_SUCCESS;
    }

    @Override
    public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private SRPCredentials getSRPCredentials(String username) {
        // TODO: get from db
        return new SRPCredentials();
    }

    private AuthStatus step1(MessageInfo messageInfo) {
        SRPCredentials credentials = getSRPCredentials((String) messageInfo.getRequestMessage());
//        srp6Server.init(N, g, credentials.getPasswordVerifier(), digest, random);
        messageInfo.setResponseMessage(credentials.getSalt());
        return AuthStatus.SEND_CONTINUE;
    }

    private AuthStatus step2(MessageInfo messageInfo) {
        ephemeralClientPublicKey = (BigInteger) messageInfo.getRequestMessage();
        BigInteger ephemeralServerPublicKey = srp6Server.generateServerCredentials();
        SRPChallenge challenge = new SRPChallenge(ephemeralServerPublicKey);
        messageInfo.setResponseMessage(challenge);
        return AuthStatus.SEND_CONTINUE;
    }

    private AuthStatus step3(MessageInfo messageInfo) throws CryptoException {
        BigInteger M1 = (BigInteger) messageInfo.getRequestMessage();
        BigInteger secret = srp6Server.calculateSecret(ephemeralClientPublicKey);
        BigInteger M2 = hashSecret(secret);
        if (M1.equals(M2)) {
            return AuthStatus.SEND_SUCCESS;
        } else {
            return AuthStatus.SEND_FAILURE;
        }

    }

    private BigInteger hashSecret(BigInteger secret) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
