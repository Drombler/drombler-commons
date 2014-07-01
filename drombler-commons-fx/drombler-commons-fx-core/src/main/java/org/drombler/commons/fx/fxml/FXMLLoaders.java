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
package org.drombler.commons.fx.fxml;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import org.drombler.commons.client.util.ResourceBundleUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.ResourceFileNotFoundException;

/**
 * Utility methods for {@link FXMLLoader}.
 *
 * @author puce
 */
public class FXMLLoaders {

    private static final Logger LOG = LoggerFactory.getLogger(FXMLLoaders.class);
    private static final String FXML_EXTENSION = ".fxml";
    private static final String CLASS_EXTENSION = ".class";
    private static final String RESOURCE_PATH_DELIMITER = "/";

    private FXMLLoaders() {
    }

    /**
     * Creates a new {@link FXMLLoader}. <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     * <li>the {@link ResourceBundle} by looking for a {@code  <name>.properties} file, where {@code <name>} is equal to
     * the name of the specified type (or a locale specific derivation using the default {@link Locale})</li>
     * </ul>
     *
     * @param type the type specifing the {@link ClassLoader} and the name of the properties file
     * @return a {@link FXMLLoader}
     */
    public static FXMLLoader createFXMLLoader(Class<?> type) {
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundleUtils.getClassResourceBundle(type);
        } catch (MissingResourceException e) {
            LOG.debug(e.getMessage());
        }
        return createFXMLLoader(type, resourceBundle);
    }

    private static FXMLLoader createFXMLLoader(Class<? extends Object> type, ResourceBundle resourceBundle) {
        FXMLLoader loader = new FXMLLoader();
        loader.setClassLoader(type.getClassLoader());
        loader.setResources(resourceBundle);
        setLocation(loader, type);
        return loader;
    }

    private static void setLocation(FXMLLoader loader, Class<? extends Object> type) {
        String locationURLString = getLocationURLString(type);
        try {
            loader.setLocation(new URL(locationURLString));
        } catch (MalformedURLException ex) {
            LOG.error(ex.getMessage() + ": Location: " + locationURLString, ex);
        }
    }

    private static String getLocationURLString(Class<? extends Object> type) {
        final String absoluteClassResourcePath = getAbsoluteClassResourcePath(type);
        String locationURLString = type.getResource(absoluteClassResourcePath).toExternalForm();
        locationURLString = locationURLString.substring(0, locationURLString.lastIndexOf(RESOURCE_PATH_DELIMITER) + 1);
        return locationURLString;
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, where &lt;class name&gt; is the type of the specified rootController and
     * the FXML-file is expected to be in the same package.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     * <li>the {@link ResourceBundle} by looking for a {@code  <name>.properties} file, where {@code <name>} is equal to
     * the name of the type of the rootController (or a locale specific derivation using the default
     * {@link Locale})</li>
     * </ul>
     *
     * The root element of the FXML document is expected to be:
     * <br> <br> {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br> <br>
     * where "super-type" is the super type of the type of the specified rootController.
     *
     * @param rootController the Object acting as the root and as the controller.
     * @throws IOException
     */
    public static void loadRoot(final Object rootController) throws IOException {
        loadRoot(rootController.getClass(), rootController);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, which is expected to be in the same package as the specified type.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     * <li>the {@link ResourceBundle} by looking for a {@code  <name>.properties} file, where {@code <name>} is equal to
     * the name of the specified type (or a locale specific derivation using the default {@link Locale})</li>
     * </ul>
     *
     * The root element of the FXML document is expected to be:
     * <br> <br> {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br> <br>
     * where "super-type" is the super type of the specified type.
     * <br> <br>
     * TODO: needed?
     *
     * @param type the type
     * @param rootController the Object acting as the root and as the controller.
     * @throws IOException
     */
    public static void loadRoot(final Class<?> type, final Object rootController) throws IOException {
        FXMLLoader loader = createFXMLLoader(type);
        loadRoot(loader, rootController, type);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, where &lt;class name&gt; is the type of the specified rootController and
     * the FXML-file is expected to be in the same package.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     * <li>the {@link ResourceBundle} to the specified resourceBundle</li>
     * </ul>
     *
     * The root element of the FXML document is expected to be:
     * <br> <br> {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br> <br>
     * where "super-type" is the super type of the type of the specified rootController.
     *
     * @param rootController the Object acting as the root and as the controller.
     * @param resourceBundle the {@link ResourceBundle} the {@link FXMLLoader} should use.
     * @throws IOException
     */
    public static void loadRoot(final Object rootController, final ResourceBundle resourceBundle)
            throws IOException {
        final Class<? extends Object> type = rootController.getClass();
        FXMLLoader loader = createFXMLLoader(type, resourceBundle);
        loadRoot(loader, rootController, type);
    }

    private static void loadRoot(FXMLLoader loader, final Object rootController, final Class<?> type) throws IOException {
        loader.setRoot(rootController);
        loader.setController(rootController);
//        loader.setControllerFactory((Class<?> param) -> rootController);
        load(loader, type);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, which is expected to be in the same package as the specified type.
     *
     * @param <T> the type of the root element
     * @param loader the {@link FXMLLoader}
     * @param type the type
     * @return the loaded object
     * @throws IOException
     */
    public static <T> T load(FXMLLoader loader, Class<?> type) throws IOException {
        try (InputStream is = getFXMLInputStream(type)) {
            if (is == null) {
                // avoid NullPointerException
                throw new ResourceFileNotFoundException(getAbsoluteFxmlResourcePath(type));
            }
            return loader.load(is);
        }
    }

    private static InputStream getFXMLInputStream(Class<?> type) {
        return type.getResourceAsStream(getFxmlFileName(type));
    }

    private static String getAbsoluteClassResourcePath(Class<?> type) {
        return getAbsolutePackageResourcePath(type.getPackage()) + RESOURCE_PATH_DELIMITER + type.getSimpleName() + CLASS_EXTENSION;
    }

    private static String getAbsoluteFxmlResourcePath(Class<?> type) {
        return getAbsolutePackageResourcePath(type.getPackage()) + RESOURCE_PATH_DELIMITER + getFxmlFileName(type);
    }

    private static String getAbsolutePackageResourcePath(Package typePackage) {
        return RESOURCE_PATH_DELIMITER + typePackage.getName().replace(".", RESOURCE_PATH_DELIMITER);
    }

    private static String getFxmlFileName(Class<?> type) {
        return type.getSimpleName() + FXML_EXTENSION;
    }
}
