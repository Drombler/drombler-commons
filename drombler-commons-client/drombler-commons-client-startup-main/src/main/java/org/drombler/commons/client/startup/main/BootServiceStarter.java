/*
 *         COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Notice
 *
 * The contents of this file are subject to the COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL)
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/cddl1.txt
 *
 * The Original Code is Drombler.org. The Initial Developer of the
 * Original Code is Florian Brunner (GitHub user: puce77).
 * Copyright 2015 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.startup.main;

public interface BootServiceStarter {

    boolean init() throws Exception;

    void startAndWait() throws Exception;

    void stop() throws Exception;

    boolean isActive();
    
    /**
     * If true, prevents the whole application from starting if initialization this starter throws some exception.
     * @return true, if required, else false.
     */
    boolean isRequired();

    public String getName();

    public boolean isRunning();

}
