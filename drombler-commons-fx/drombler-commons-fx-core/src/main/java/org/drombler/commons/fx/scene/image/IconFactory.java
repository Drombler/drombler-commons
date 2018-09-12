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
package org.drombler.commons.fx.scene.image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.drombler.commons.client.graphic.GraphicFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.softsmithy.lib.util.ResourceLoader;

/**
 * A Icon factory.
 *
 * This factory works with a naming pattern.
 *
 * If the icon naming pattern is {@code <icon-location>/<icon-base-name>.<icon-extension>}, then this factory looks for
 * {@code <icon-location>/<icon-base-name><size>.<icon-extension>} and scales it to the requested size.
 *
 * E.g.
 *
 * {@code
 * GraphicFactory graphicFactory = new IconFactory("/somePath/save.png", new ResourceLoader(MyClass.class), false);
 *
 * Node menuItemGraphic = graphicFactory.createGraphic(16); // will look for "/somePath/save16.png"
 * Node toolbarButtonGraphic = graphicFactory.createGraphic(24); // will look for "/somePath/save24.png"
 * }
 *
 * @author puce
 */
public class IconFactory implements GraphicFactory<Node> {

    private static final Logger LOG = LoggerFactory.getLogger(IconFactory.class);

    private final Map<Integer, Image> images = new HashMap<>();
    private final String iconResourcePath;
    private final ResourceLoader resourceLoader;
    private final boolean smoothIcon;

    /**
     * Creates a new instance of this class.
     *
     * @param icon the icon name pattern.
     * @param resourceLoader the ResourceLoader
     * @param smoothIcon the scaling algorithm
     */
    public IconFactory(String icon, ResourceLoader resourceLoader, boolean smoothIcon) {
        this.iconResourcePath = icon;
        this.resourceLoader = resourceLoader;
        this.smoothIcon = smoothIcon;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public ImageView createGraphic(int size) {
        final Image iconImage = getIconImage(size);
        if (iconImage != null) {
            return new ImageView(iconImage);
        } else {
            return null;
        }
    }

    private Image getIconImage(int size) {
        if (!images.containsKey(size)) {
            Image image = loadImage(size);
            if (image != null) { // TODO: add null?
                images.put(size, image);
            }
        }
        return images.get(size);

    }

    private Image loadImage(int size) {
        InputStream imageInputStream = getImageInputStream(size);
        if (imageInputStream != null) {
            try (InputStream is = imageInputStream) {
                return new Image(is, size, size, true, smoothIcon);
            } catch (Exception ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
        return null;
    }

    private InputStream getImageInputStream(int size) {
        String currentIconResourcePath = getIconResourcePath(size);
        if (currentIconResourcePath != null) {
            return resourceLoader.getResourceAsStream(currentIconResourcePath);
        } else {
            return null;
        }
    }

    protected String getIconResourcePath(int size) {
        String currentIconResourcePath = iconResourcePath;
        if (currentIconResourcePath != null) {
            String[] iconNameParts = currentIconResourcePath.split("\\.");
            if (iconNameParts.length > 0) {
                StringBuilder sb = new StringBuilder(iconNameParts[0]);
                sb.append(size);
                for (int i = 1; i < iconNameParts.length; i++) {
                    sb.append(".");
                    sb.append(iconNameParts[i]);
                }
                currentIconResourcePath = sb.toString();
            }
        }
        return currentIconResourcePath;
    }
}
