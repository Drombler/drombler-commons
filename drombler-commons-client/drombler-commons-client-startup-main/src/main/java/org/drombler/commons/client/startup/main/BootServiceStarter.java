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

/**
 * A starter for a boot time service.
 *
 * @author puce
 */
public interface BootServiceStarter {

    /**
     * Initializes this starter.
     *
     * @return true, if the starter could successfully be initialized, else false
     * @throws Exception if an unexpected error occured
     */
    boolean init() throws Exception;

    /**
     * Starts this starter and waits (blocks the current thread). Call this method only after a successful {@link #init() } call.
     *
     * @throws Exception if an unexpected error occured
     * @see #init()
     */
    void startAndWait() throws Exception;

    /**
     * Stops this starter.
     *
     * @throws Exception if an unexpected error occured
     */
    void stop() throws Exception;

    /**
     * Indicates if this starter should be used.
     *
     * @return true, if active, else false
     */
    boolean isActive();

    /**
     * If true, prevents the whole application from starting if initialization this starter was not successful.
     *
     * @return true, if required, else false.
     */
    boolean isRequired();

    /**
     * Gets the name of this starter.
     *
     * @return the name of this starter
     */
    public String getName();

    /**
     * Indicates if this starter is running.
     *
     * @return true, if this starter is running, else false
     */
    public boolean isRunning();

}
