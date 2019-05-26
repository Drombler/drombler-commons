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
 * The application configuration packaged with the application.
 *
 * This configuration should usually not be configurable by the user.
 */
public class ApplicationConfiguration {

    public static final String APPLICATION_PROPERTIES_FILE_PATH_RELATIVE = "applicationConfig.properties";

    public static final String APPLICATION_PROPERTIES_FILE_PATH_ABSOLUTE = "/" + APPLICATION_PROPERTIES_FILE_PATH_RELATIVE;

    public static final String APPLICATION_DEFAULT_SINGLE_INSTANCE_PORT_PROPERTY_NAME
            = "drombler.application.defaultSingleInstancePort";

    private final Map<String, String> applicationConfig;

    /**
     * Creates a new instance of this class and loads the application configuration from the file '/applicationConfig.properties', if available.
     */
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

    /**
     * Gets the double value associated with the specified key.
     *
     * @param key the key
     * @param defaultValue the default value
     * @param errorMessageFormat the error message format
     * @return the double value associated with the specified key
     */
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

    /**
     * Gets the positive double value associated with the specified key.
     *
     * @param key the key
     * @param defaultValue the default value returned if the value could not be loaded or was not positive
     * @param errorMessageFormat the error message format
     * @return the double value associated with the specified key
     */
    public double getPositiveDoubleProperty(String key, double defaultValue, String errorMessageFormat) {
        double value = getDoubleProperty(key, -1, errorMessageFormat);
        if (value <= 0) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Gets the integer value associated with the specified key.
     *
     * @param key the key
     * @param defaultValue the default value
     * @param errorMessageFormat the error message format
     * @return the integer value associated with the specified key
     */
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

    /**
     * Gets the String value associated with the specified key.
     *
     * @param key the key
     * @return the String value associated with the specified key
     */
    public String getStringProperty(String key) {
        return applicationConfig.get(key);
    }

    /**
     * Flag if this is a single instance application.
     *
     * @return true, if this is a single instance application, else false
     */
    public boolean isSingleInstanceApplication() {
        return applicationConfig.containsKey(APPLICATION_DEFAULT_SINGLE_INSTANCE_PORT_PROPERTY_NAME);
    }

    /**
     * Gets the configured default single instance port.
     *
     * @return the configured default single instance port
     */
    public int getDefaultSingleInstancePort() {
        return getIntegerProperty(APPLICATION_DEFAULT_SINGLE_INSTANCE_PORT_PROPERTY_NAME, null, // TODO: default?
                "The default single instance port is not an integer: {}");
    }
}
