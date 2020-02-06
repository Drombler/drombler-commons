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
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.client.startup.main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author puce
 */
public class SimpleAdditionalArgumentsProvider implements AdditionalArgumentsProvider {

    private final List<String> additionalArguments;

    public SimpleAdditionalArgumentsProvider(List<String> additionalArguments) {
        this.additionalArguments = Collections.unmodifiableList(new ArrayList<>(additionalArguments));
    }

    @Override
    public List<String> getAdditionalArguments() {
        return additionalArguments;
    }

}
