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
