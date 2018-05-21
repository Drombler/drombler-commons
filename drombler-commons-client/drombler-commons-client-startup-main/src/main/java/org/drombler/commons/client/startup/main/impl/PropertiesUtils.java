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
package org.drombler.commons.client.startup.main.impl;

import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 *
 * @author puce
 */
// Attention: copied from (not moved) org.drombler.acp.startup.main.impl.PropertiesUtils
// TODO: move to SoftSmithy? startup dependency?
public final class PropertiesUtils {

    private PropertiesUtils() {
    }

    public static Map<String, String> toMap(Properties properties) {
        return properties.stringPropertyNames().stream().
                collect(Collectors.toMap(propertyName -> propertyName,
                                propertyName -> properties.getProperty(propertyName)));
    }
}
