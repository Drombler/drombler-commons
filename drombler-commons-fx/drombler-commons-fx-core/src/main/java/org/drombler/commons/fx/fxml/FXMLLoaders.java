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
    private static final String RESOURCE_PATH_DELIMITER = "/";

    private FXMLLoaders() {
    }

    /**
     * Creates a new {@link FXMLLoader}. <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ClassLoader}</li>
     * </ul>
     *
     * @param classLoader the {@link ClassLoader}
     * @return a {@link FXMLLoader}
     */
    public static FXMLLoader createFXMLLoader(ClassLoader classLoader) {
        FXMLLoader loader = new FXMLLoader();
        loader.setClassLoader(classLoader);
        return loader;
    }

    private static ResourceBundle getClassResourceBundle(Class<?> type) {
        ResourceBundle resourceBundle = null;
        try {
            resourceBundle = ResourceBundleUtils.getClassResourceBundle(type);
        } catch (MissingResourceException e) {
            LOG.debug(e.getMessage());
        }
        return resourceBundle;
    }

    private static void configureFXMLLoader(FXMLLoader loader, URL location, ResourceBundle resourceBundle) {
        loader.setResources(resourceBundle);
        loader.setLocation(location);
    }

    /**
     * Resets the following properties of the specified {@link FXMLLoader} to null:
     * <ul>
     * <li>root</li>
     * <li>controller</li>
     * <li>controllerFactory</li>
     * <li>resources</li>
     * <li>location</li>
     * </ul>
     * TODO: needed?
     *
     * @param loader
     * @see #loadRoot(javafx.fxml.FXMLLoader, java.lang.Object)
     * @see #loadRoot(javafx.fxml.FXMLLoader, java.lang.Object, java.util.ResourceBundle)
     * @see #load(javafx.fxml.FXMLLoader, java.lang.Class, java.util.ResourceBundle)
     */
    public static void resetFXMLLoader(FXMLLoader loader) {
        loader.setRoot(null);
        loader.setController(null);
        loader.setControllerFactory(null);
        loader.setResources(null);
        loader.setLocation(null);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, where &lt;class name&gt; is the type of the specified rootController and
     * the FXML-file is expected to be in the same package.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     * <li>the root</li>
     * <li>the controller</li>
     * <li>the {@link ResourceBundle} by looking for a {@code  <name>.properties} file, where {@code <name>} is equal to
     * the name of the type of the rootController (or a locale specific derivation using the default
     * {@link Locale})</li>
     * <li>the location to the FXML file</li>
     * </ul>
     *
     * The root element of the FXML document is expected to be:
     * <br> <br> {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br> <br>
     * where "super-type" is the super type of the type of the specified rootController.
     *
     * @param rootController the Object acting as the root and as the controller.
     */
    public static void loadRoot(final Object rootController) {
        FXMLLoader loader = createFXMLLoader(rootController.getClass().getClassLoader());
        loadRoot(loader, rootController);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, where &lt;class name&gt; is the type of the specified rootController and
     * the FXML-file is expected to be in the same package.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the root</li>
     * <li>the controller</li>
     * <li>the {@link ResourceBundle} by looking for a {@code  <name>.properties} file, where {@code <name>} is equal to
     * the name of the specified type (or a locale specific derivation using the default {@link Locale})</li>
     * <li>the location to the FXML file</li>
     * </ul>
     *
     * The root element of the FXML document is expected to be:
     * <br> <br> {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br> <br>
     * where "super-type" is the super type of the specified type.
     * <br> <br>
     * TODO: needed?
     *
     * @param loader the {@link FXMLLoader}
     * @param rootController the Object acting as the root and as the controller.
     * @see #resetFXMLLoader(javafx.fxml.FXMLLoader)
     */
    public static void loadRoot(final FXMLLoader loader, final Object rootController) {
        configureRootController(loader, rootController);
        load(loader, rootController.getClass());
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, where &lt;class name&gt; is the type of the specified rootController and
     * the FXML-file is expected to be in the same package.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ClassLoader} to the ClassLoader of the specified type</li>
     * <li>the root</li>
     * <li>the controller</li>
     * <li>the {@link ResourceBundle} to the specified resourceBundle</li>
     * <li>the location to the FXML file</li>
     * </ul>
     *
     * The root element of the FXML document is expected to be:
     * <br> <br> {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br> <br>
     * where "super-type" is the super type of the type of the specified rootController.
     *
     * @param rootController the Object acting as the root and as the controller.
     * @param resourceBundle the {@link ResourceBundle} the {@link FXMLLoader} should use.
     */
    public static void loadRoot(final Object rootController, final ResourceBundle resourceBundle) {
        final FXMLLoader loader = createFXMLLoader(rootController.getClass().getClassLoader());
        loadRoot(loader, rootController, resourceBundle);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, where &lt;class name&gt; is the type of the specified rootController and
     * the FXML-file is expected to be in the same package.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the root</li>
     * <li>the controller</li>
     * <li>the {@link ResourceBundle} to the specified resourceBundle</li>
     * <li>the location to the FXML file</li>
     * </ul>
     *
     * The root element of the FXML document is expected to be:
     * <br> <br> {@code  <fx:root type="{super-type}" xmlns:fx="http://javafx.com/fxml">}
     * <br> <br>
     * where "super-type" is the super type of the type of the specified rootController.
     *
     * @param loader the {@link FXMLLoader}
     * @param rootController the Object acting as the root and as the controller.
     * @param resourceBundle the {@link ResourceBundle} the {@link FXMLLoader} should use.
     * @see #resetFXMLLoader(javafx.fxml.FXMLLoader)
     */
    public static void loadRoot(final FXMLLoader loader, final Object rootController,
            final ResourceBundle resourceBundle) {
        configureRootController(loader, rootController);
        load(loader, rootController.getClass(), resourceBundle);
    }

    private static void configureRootController(FXMLLoader loader, final Object rootController) {
        loader.setRoot(rootController);
        loader.setController(rootController);
//        loader.setControllerFactory((Class<?> param) -> rootController);
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, which is expected to be in the same package as the specified type.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ResourceBundle} by looking for a {@code  <name>.properties} file, where {@code <name>} is equal to
     * the name of the specified type (or a locale specific derivation using the default {@link Locale})</li>
     * <li>the location to the FXML file</li>
     * </ul>
     *
     * @param <T> the type of the root element
     * @param loader the {@link FXMLLoader}
     * @param type the type
     * @return the loaded object
     * @see #resetFXMLLoader(javafx.fxml.FXMLLoader)
     */
    public static <T> T load(FXMLLoader loader, Class<?> type) {
        return load(loader, type, getClassResourceBundle(type));
    }

    /**
     * Loads the &lt;class name&gt;.fxml file, which is expected to be in the same package as the specified type.
     * <br> <br>
     * Sets:
     * <ul>
     * <li>the {@link ResourceBundle} to the specified resourceBundle</li>
     * <li>the location to the FXML file</li>
     * </ul>
     *
     * @param <T> the type of the root element
     * @param loader the {@link FXMLLoader}
     * @param type the type
     * @param resourceBundle the {@link ResourceBundle} the {@link FXMLLoader} should use.
     * @return the loaded object
     * @see #resetFXMLLoader(javafx.fxml.FXMLLoader)
     */
    public static <T> T load(final FXMLLoader loader, final Class<?> type, final ResourceBundle resourceBundle) {
        configureFXMLLoader(loader, getFXMLLocation(type), resourceBundle);
        return loadFXML(loader, type);
    }

    private static <T> T loadFXML(FXMLLoader loader, Class<?> type) {
        try (InputStream is = getFXMLInputStream(type)) {
            if (is == null) {
                // avoid NullPointerException
                throw new ResourceFileNotFoundException(getAbsoluteFxmlResourcePath(type));
            }
            return loader.load(is);
        } catch (IOException ex) {
            // The FXML gets loaded from the classpath here. 
            // The IOException cannot reasonably be handled -> RuntimeException
            // TODO: consider org.softsmithy.lib.io.IORuntimeException
            throw new RuntimeException(ex);
        }
    }

    private static InputStream getFXMLInputStream(Class<?> type) {
        return type.getResourceAsStream(getFxmlFileName(type));
    }

    private static URL getFXMLLocation(Class<?> type) {
        return type.getResource(getFxmlFileName(type));
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
