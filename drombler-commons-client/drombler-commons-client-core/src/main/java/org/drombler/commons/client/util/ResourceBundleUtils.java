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
 * A utility class to work with {@link ResourceBundle}s.
 *
 * @author puce
 */
public final class ResourceBundleUtils {

    /**
     * The resource bundle base name of package resource bundles.
     */
    public static final String PACKAGE_RESOURCE_BUNDLE_BASE_NAME = "Bundle";

    /**
     * The prefix for resource keys to mark them as I18N keys.
     */
    public static final String KEY_PREFIX = "%";

    private ResourceBundleUtils() {
    }

    /**
     * Gets the {@link ResourceBundle} if the resource key is prefixed with '%', else null (no I18N).<br>
     * <br>
     * <pre>
     * If resourceBundleBaseName is null, the {@link #getClassResourceBundle(java.lang.Class) } gets returned.
     * If resourceBundleBaseName equals 'Bundle', the {@link #getPackageResourceBundle(java.lang.Class)} gets returned.
     * Else the ResourceBundle for the resourceBundleBaseName gets returned using the same {@link ClassLoader} as the provided type.
     * </pre>
     *
     * @param type the type
     * @param resourceBundleBaseName the base name of the ResourceBundle, 'Bundle' (for the package ResourceBundle) or null (for the class ResourceBundle).
     * @param resourceKey the prefixed resourceKey or the value to return (no I18N)
     * @return the ResoureBundle if the resource key is prefixed with '%', else null (no I18N)
     *
     * @see #getClassResourceBundle(java.lang.Class)
     * @see #getPackageResourceBundle(java.lang.Class)
     * @see #KEY_PREFIX
     * @see #PACKAGE_RESOURCE_BUNDLE_BASE_NAME
     */
    // TODO: useful?
    public static ResourceBundle getResourceBundle(Class<?> type, String resourceBundleBaseName, String resourceKey) {
        ResourceBundle resourceBundle = null;
        resourceKey = StringUtils.stripToNull(resourceKey);
        if (isPrefixedResourceString(resourceKey)) {
            resourceBundleBaseName = StringUtils.stripToNull(resourceBundleBaseName);
            if (resourceBundleBaseName == null) {
                resourceBundle = getClassResourceBundle(type);
            } else {
                if (resourceBundleBaseName.equals(PACKAGE_RESOURCE_BUNDLE_BASE_NAME)) {
                    resourceBundle = getPackageResourceBundle(type);
                } else {
                    resourceBundle = getResourceBundle(resourceBundleBaseName, type.getClassLoader());
                }
            }
        }
        return resourceBundle;
    }

    /**
     * Gets the resource bundle, which is in the same package as the provided type and has a base name equal to the simple name of the provided class.
     *
     * @param type the type
     * @return the resource bundle for the type
     */
    public static ResourceBundle getClassResourceBundle(Class<?> type) {
        return getResourceBundle(type, type.getSimpleName());
    }

    /**
     * Gets a resource string from the class-{@link ResourceBundle} (same package and same base name as the provided type), if the {@code  resourceKey} is prefixed with {@literal '%'}, else the resoure
     * key itself gets returned (no I18N).
     *
     * @param type the type of the same package and the same simple name as the base name of the ResourceBundle
     * @param resourceKey the prefixed resourceKey or the value to return (no I18N)
     * @return if the resourceKey is prefixed, the value from the class-ResourceBundle, else the resourceKey (no I18N)
     * @see #KEY_PREFIX
     */
    // TODO: needed?
    public static String getClassResourceStringPrefixed(Class<?> type, String resourceKey) {
        return getResourceStringPrefixed(resourceKey, type.getPackage().getName(), type.getSimpleName(), type.
                getClassLoader());
    }

    /**
     * Gets the package-{@link ResourceBundle} (Bundle.properties), which is in the same package as the specified type.
     *
     * @param type a type of the same package
     * @return the package ResourceBundle
     * @see #PACKAGE_RESOURCE_BUNDLE_BASE_NAME
     */
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

    /**
     * Gets a resource string from the package-{@link ResourceBundle} (Bundle.properties), if the {@code  resourceKey} is prefixed with {@literal '%'}, else the resoure key itself gets returned (no
     * I18N).
     *
     * @param type a type of the package
     * @param resourceKey the prefixed key
     * @return the package resource for the prefixed key, if the key is prefixed, else the resource key gets returned
     * @see #KEY_PREFIX
     */
    public static String getPackageResourceStringPrefixed(Class<?> type, String resourceKey) {
        return getPackageResourceStringPrefixed(type.getPackage().getName(), resourceKey, type.getClassLoader());
    }

    /**
     * Gets a resource string from the package-{@link ResourceBundle} (Bundle.properties), if the {@code  resourceKey} is prefixed with {@literal '%'}, else the resoure key itself gets returned (no
     * I18N).
     *
     * @param aPackage the package
     * @param resourceKey the prefixed resourceKey or the value to return (no I18N)
     * @param classLoader the ClassLoader to load the ResourceBundle
     * @return if the resourceKey is prefixed, the value from the package-ResourceBundle, else the resourceKey (no I18N)
     * @see #KEY_PREFIX
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
     * If the resource key starts with '%' prefix, the prefix gets stripped and the value gets read from the provided {@link ResourceBundle}, else the provided resourceKey will be returned without
     * lookup (no I18N).
     *
     * @param resourceKey the prefixed resourceKey or the value to return (no I18N)
     * @param resourceBundle the ResourceBundle to lookup values
     * @exception MissingResourceException if no value for the specified key can be found
     * @exception ClassCastException if the value is not a {@link String}
     * @return if the resourceKey is prefixed, the value from the ResourceBundle, else the resourceKey (no I18N)
     * @see #KEY_PREFIX
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
