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
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */package org.drombler.commons.logging.slf4j.cdi.impl;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;
import org.slf4j.Logger;

/**
 *
 * @author puce
 */
@ApplicationScoped
@OsgiServiceProvider
public class TestBean {

    @Inject
    private Logger logger;

    public void test() {
        logger.info("test");
    }
}
