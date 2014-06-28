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
package org.drombler.commons.client.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author puce
 */
public class ResourceBundleUtils {

    public static final String PACKAGE_RESOURCE_BUNDLE_BASE_NAME = "Bundle";
    public static final String KEY_PREFIX = "%";

    private ResourceBundleUtils() {
    }

    public static ResourceBundle getResourceBundle(Class<?> type, String resourceBundleBaseName, String resourceKey) {
        ResourceBundle resourceBundle = null;
        if (isPrefixedResourceString(resourceKey)) {
            resourceBundleBaseName = StringUtils.stripToNull(resourceBundleBaseName);
            if (resourceBundleBaseName == null) {
                resourceBundle = getClassResourceBundle(type);
            } else if (resourceBundleBaseName.equals(PACKAGE_RESOURCE_BUNDLE_BASE_NAME)) {
                resourceBundle = getPackageResourceBundle(type);
            } else {
                resourceBundle = getResourceBundle(resourceBundleBaseName, type.getClassLoader());
            }
        }
        return resourceBundle;
    }

    public static ResourceBundle getClassResourceBundle(Class<?> type) {
        return getResourceBundle(type, type.getSimpleName());
    }
    // TODO: needed?

    public static String getClassResourceStringPrefixed(Class<?> type, String resourceKey) {
        return getResourceStringPrefixed(resourceKey, type.getPackage().getName(), type.getSimpleName(), type.
                getClassLoader());
    }

    public static ResourceBundle getPackageResourceBundle(Class<?> type) {
        return getResourceBundle(type, PACKAGE_RESOURCE_BUNDLE_BASE_NAME);
    }

    private static ResourceBundle getResourceBundle(Class<?> type, String baseName) {
        return getResourceBundle(type.getPackage().getName(), baseName, type.getClassLoader());
    }

    private static ResourceBundle getResourceBundle(String aPackage, String baseName, ClassLoader classLoader) {
        return getResourceBundle(aPackage + "." + baseName, classLoader);
    }

    private static ResourceBundle getResourceBundle(String resourceBundleBaseName, ClassLoader classLoader) {
        return ResourceBundle.getBundle(resourceBundleBaseName, Locale.getDefault(), classLoader);
    }

    public static String getPackageResourceStringPrefixed(Class<?> type, String resourceKey) {
        return getPackageResourceStringPrefixed(type.getPackage().getName(), resourceKey, type.getClassLoader());
    }

    /**
     * Gets a resource string from the package-{@link ResourceBundle} (Bundle.properties), if the {@code  resourceKey} is
     * prefixed with {@literal '%'}, else the resoure key itself gets returned.
     *
     * @param aPackage
     * @param resourceKey
     * @param classLoader
     * @return
     */
    public static String getPackageResourceStringPrefixed(String aPackage, String resourceKey, ClassLoader classLoader) {
        return getResourceStringPrefixed(resourceKey, aPackage, PACKAGE_RESOURCE_BUNDLE_BASE_NAME, classLoader);
    }

    private static String getResourceStringPrefixed(String resourceKey, String aPackage, String baseName,
            ClassLoader classLoader) {
        String strippedResourceKey = StringUtils.stripToNull(resourceKey);
        if (isPrefixedResourceString(strippedResourceKey)) {
            ResourceBundle rb = getResourceBundle(aPackage, baseName, classLoader);
            return getResourceStringPrefixed(resourceKey, rb);
        }
        return resourceKey;
    }

    private static boolean isPrefixedResourceString(String strippedResourceKey) {
        return strippedResourceKey != null && strippedResourceKey.startsWith(KEY_PREFIX);
    }

   
    /**
     *
     * @param resourceKey
     * @param resourceBundle
     * @exception NullPointerException if the key is null
     * @exception MissingResourceException if no value for the specified key can be found
     * @exception ClassCastException if the value is not a {@link String}
     * @return
     */
    public static String getResourceStringPrefixed(String resourceKey, ResourceBundle resourceBundle) {
        String strippedResourceKey = StringUtils.stripToNull(resourceKey);
        if (isPrefixedResourceString(strippedResourceKey)) {
            strippedResourceKey = strippedResourceKey.substring(KEY_PREFIX.length());
//            if (rb.containsKey(resourceKey)) {
            return resourceBundle.getString(strippedResourceKey);
//            }
        }
        return resourceKey;
    }
}
