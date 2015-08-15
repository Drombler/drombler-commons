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

import javax.annotation.PostConstruct;
import org.drombler.commons.logging.slf4j.cdi.ITestBean;
import org.ops4j.pax.cdi.api.OsgiServiceProvider;

/**
 *
 * @author puce
 */
//@ApplicationScoped
@OsgiServiceProvider
//@Dependent
public class TestBean implements ITestBean {

//    @Inject
//    @OsgiService
//    private Logger logger;

    @PostConstruct
    public void test() {
//        logger.error("test!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public void someTest() {
//        logger.error("sometest!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
