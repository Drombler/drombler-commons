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
 * Copyright 2019 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.startup.main;

import java.util.EventObject;
import java.util.List;

/**
 * An application instance event.
 */
public class ApplicationInstanceEvent extends EventObject {

    private static final long serialVersionUID = 5516075349620653480L;

    private final List<String> additionalArgs;

    /**
     * Creates a new instance of this class.
     *
     * @param source the source of this event
     * @param additionalArgs the additional args passed to the application
     */
    public ApplicationInstanceEvent(Object source, List<String> additionalArgs) {
        super(source);
        this.additionalArgs = additionalArgs;
    }

    /**
     * Gets the additional args.
     *
     * @return the additional args
     */
    public List<String> getAdditionalArgs() {
        return additionalArgs;
    }

}
