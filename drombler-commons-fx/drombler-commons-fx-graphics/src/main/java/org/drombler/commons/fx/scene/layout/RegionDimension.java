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
 * A {@link Region] dimension.
 * @author puce
 */
public enum RegionDimension {

    /**
     * The width of a {@link Region}.
     */
    WIDTH {
        /**
         * {@inheritDoc }
         */
        @Override
        public double getSize(Region region) {
            return region.getWidth();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public double getPrefSize(Region region) {
            return region.getPrefWidth();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void setPrefSize(Region region, double prefSize) {
            region.setPrefWidth(prefSize);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public double getMinSize(Region region) {
            return region.getMinWidth();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void setMinSize(Region region, double minSize) {
            region.setMinWidth(minSize);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public double getMaxSize(Region region) {
            return region.getMaxWidth();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void setMaxSize(Region region, double maxSize) {
            region.setMaxWidth(maxSize);
        }

    },
    /**
     * The height of a {@link Region}.
     */
    HEIGHT {
        /**
         * {@inheritDoc }
         */
        @Override
        public double getSize(Region region) {
            return region.getHeight();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public double getPrefSize(Region region) {
            return region.getPrefHeight();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void setPrefSize(Region region, double prefSize) {
            region.setPrefHeight(prefSize);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public double getMinSize(Region region) {
            return region.getMinHeight();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void setMinSize(Region region, double minSize) {
            region.setMinHeight(minSize);
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public double getMaxSize(Region region) {
            return region.getMaxHeight();
        }

        /**
         * {@inheritDoc }
         */
        @Override
        public void setMaxSize(Region region, double maxSize) {
            region.setMaxHeight(maxSize);
        }
    };

    /**
     * Gets the size of a region dimension.
     *
     * @param region the region
     * @return the size of a region dimension
     */
    public abstract double getSize(Region region);

    /**
     * Gets the preferred size of a region dimension.
     *
     * @param region the region
     * @return the preferred size of a region dimension
     */
    public abstract double getPrefSize(Region region);

    /**
     * Sets the preferred size of a region dimension.
     *
     * @param region the region
     * @param prefSize the preferred size of a region dimension
     */
    public abstract void setPrefSize(Region region, double prefSize);

    /**
     * Gets the minimum size of a region dimension.
     *
     * @param region the region
     * @return the minimum size of a region dimension
     */
    public abstract double getMinSize(Region region);

    /**
     * Sets the minimum size of a region dimension.
     *
     * @param region the region
     * @param minSize the minimum size of a region dimension
     */
    public abstract void setMinSize(Region region, double minSize);

    /**
     * Gets the maximal size of a region dimension.
     *
     * @param region the region
     * @return the maximal size of a region dimension
     */
    public abstract double getMaxSize(Region region);

    /**
     * Sets the maximal size of a region dimension.
     *
     * @param region the region
     * @param maxSize the maximal size of a region dimension
     */
    public abstract void setMaxSize(Region region, double maxSize);

}
