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
import java.util.ResourceBundle;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author puce
 */
public class ResourceBundleUtils {

    public static final String PROPERTIES_FILE_BASE_NAME = "Bundle";
    public static final String KEY_PREFIX = "%";

    private ResourceBundleUtils() {
    }

    public static ResourceBundle getPackageResourceBundle(Class<?> type) {
        return getPackageResourceBundle(type, PROPERTIES_FILE_BASE_NAME);
    }

    private static ResourceBundle getPackageResourceBundle(Class<?> type, String baseName) {
        return getPackageResourceBundle(type.getPackage().getName(), baseName, type.getClassLoader());
    }

    private static ResourceBundle getPackageResourceBundle(String aPackage, ClassLoader classLoader) {
        return getPackageResourceBundle(aPackage, PROPERTIES_FILE_BASE_NAME, classLoader);
    }

    private static ResourceBundle getPackageResourceBundle(String aPackage, String baseName, ClassLoader classLoader) {
        return ResourceBundle.getBundle(aPackage + "." + baseName, Locale.getDefault(),
                classLoader);
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
        String strippedResourceKey = StringUtils.stripToNull(resourceKey);
        if (strippedResourceKey != null && strippedResourceKey.startsWith(KEY_PREFIX)) {
            strippedResourceKey = strippedResourceKey.substring(1);
            ResourceBundle rb = getPackageResourceBundle(aPackage, classLoader);
//            if (rb.containsKey(resourceKey)) {
            return rb.getString(strippedResourceKey);
//            }
        }
        return resourceKey;
    }
}
