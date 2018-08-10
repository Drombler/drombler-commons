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

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import org.drombler.commons.client.startup.main.impl.PropertiesUtils;


/**
 * The Application Configuration gets generated at build time of the application and packaged into the final JAR.
 *
 * This configuration should usually not be configurable by the user.
 */
public class ApplicationConfiguration {

    public static final String APPLICATION_PROPERTIES_FILE_PATH_RELATIVE = "applicationConfig.properties";

    public static final String APPLICATION_PROPERTIES_FILE_PATH_ABSOLUTE = "/" + APPLICATION_PROPERTIES_FILE_PATH_RELATIVE;

    public static final String APPLICATION_DEFAULT_SINGLE_INSTANCE_PORT_PROPERTY_NAME
            = "drombler.application.defaultSingleInstancePort";

    private final Map<String, String> applicationConfig;

    public ApplicationConfiguration() {
        this.applicationConfig = loadApplicationConfig();
    }

    private Map<String, String> loadApplicationConfig() {
        Properties configProperties = new Properties();

        try (InputStream is = ApplicationConfiguration.class.getResourceAsStream(APPLICATION_PROPERTIES_FILE_PATH_ABSOLUTE)) {
            if (is != null) {
                configProperties.load(is);
            }
        } catch (IOException ex) {
            System.err.println("ApplicationConfiguration: Error loading applicationConfig.properties!");
        }

        return Collections.unmodifiableMap(PropertiesUtils.toMap(configProperties));
    }

    public double getDoubleProperty(String key, double defaultValue, String errorMessageFormat) {
        double value = defaultValue;
        if (applicationConfig.containsKey(key)) {
            try {
                value = Double.parseDouble(applicationConfig.get(key));
            } catch (NumberFormatException ex) {
                System.err.println("ApplicationConfiguration: Error loading applicationConfig.properties!");

//                LOG.error(errorMessageFormat, applicationConfig.get(key));
            }
        }
        return value;
    }

    public double getPositiveDoubleProperty(String key, double defaultValue, String errorMessageFormat) {
        double value = getDoubleProperty(key, -1, errorMessageFormat);
        if (value <= 0) {
            value = defaultValue;
        }
        return value;
    }

    public Integer getIntegerProperty(String key, Integer defaultValue, String errorMessageFormat) {
        Integer value = defaultValue;
        if (applicationConfig.containsKey(key)) {
            try {
                value = Integer.parseInt(applicationConfig.get(key));
            } catch (NumberFormatException ex) {
                System.err.println("ApplicationConfiguration: Error loading applicationConfig.properties!");

//                LOG.error(errorMessageFormat, applicationConfig.get(key));
            }
        }
        return value;
    }

    public String getStringProperty(String key) {
        return applicationConfig.get(key);
    }

    public boolean isSingleInstanceApplication() {
        return applicationConfig.containsKey(APPLICATION_DEFAULT_SINGLE_INSTANCE_PORT_PROPERTY_NAME);
    }

    public int getDefaultSingleInstancePort() {
        return getIntegerProperty(APPLICATION_DEFAULT_SINGLE_INSTANCE_PORT_PROPERTY_NAME, null, // TODO: default?
                "The default single instance port is not an integer: {}");
    }
}
