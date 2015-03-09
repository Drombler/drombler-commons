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
 * Copyright 2014 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.fx.scene.layout;

import javafx.scene.layout.Region;

/**
 *
 * @author puce
 */
public enum RegionDimension {

    WIDTH {
                @Override
                public double getSize(Region region) {
                    return region.getWidth();
                }

                @Override
                public double getPrefSize(Region region) {
                    return region.getPrefWidth();
                }

                @Override
                public void setPrefSize(Region region, double prefSize) {
                    region.setPrefWidth(prefSize);
                }

                @Override
                public double getMinSize(Region region) {
                    return region.getMinWidth();
                }

                @Override
                public void setMinSize(Region region, double minSize) {
                    region.setMinWidth(minSize);
                }

                @Override
                public double getMaxSize(Region region) {
                    return region.getMaxWidth();
                }

                @Override
                public void setMaxSize(Region region, double maxSize) {
                    region.setMaxWidth(maxSize);
                }

            },
    HEIGHT {
                @Override
                public double getSize(Region region) {
                    return region.getHeight();
                }

                @Override
                public double getPrefSize(Region region) {
                    return region.getPrefHeight();
                }

                @Override
                public void setPrefSize(Region region, double prefSize) {
                    region.setPrefHeight(prefSize);
                }

                @Override
                public double getMinSize(Region region) {
                    return region.getMinHeight();
                }

                @Override
                public void setMinSize(Region region, double minSize) {
                    region.setMinHeight(minSize);
                }

                @Override
                public double getMaxSize(Region region) {
                    return region.getMaxHeight();
                }

                @Override
                public void setMaxSize(Region region, double maxSize) {
                    region.setMaxHeight(maxSize);
                }
            };

    public abstract double getSize(Region region);

    public abstract double getPrefSize(Region region);

    public abstract void setPrefSize(Region region, double prefSize);

    public abstract double getMinSize(Region region);

    public abstract void setMinSize(Region region, double minSize);

    public abstract double getMaxSize(Region region);

    public abstract void setMaxSize(Region region, double maxSize);

}
