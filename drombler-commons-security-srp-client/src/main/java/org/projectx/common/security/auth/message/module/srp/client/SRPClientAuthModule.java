/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.projectx.common.security.auth.message.module.srp.client;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.module.ClientAuthModule;
import org.bouncycastle.crypto.agreement.srp.SRP6Client;

/**
 *
 * @author puce
 */
public class SRPClientAuthModule implements ClientAuthModule{

    @Override
    public void initialize(MessagePolicy requestPolicy, MessagePolicy responsePolicy, CallbackHandler handler, Map options) throws AuthException {
        SRP6Client srpClient = new SRP6Client();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Class[] getSupportedMessageTypes() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AuthStatus secureRequest(MessageInfo messageInfo, Subject clientSubject) throws AuthException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public AuthStatus validateResponse(MessageInfo messageInfo, Subject clientSubject, Subject serviceSubject) throws AuthException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanSubject(MessageInfo messageInfo, Subject subject) throws AuthException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
