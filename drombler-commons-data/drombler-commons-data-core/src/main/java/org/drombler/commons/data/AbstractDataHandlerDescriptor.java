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
 * Copyright 2016 Drombler.org. All Rights Reserved.
 *
 * Contributor(s): .
 */
package org.drombler.commons.data;

import org.softsmithy.lib.util.ResourceLoader;

/**
 * A base class for Data Handler descriptors. It provides some common meta data to create a Data Handler.
 *
 * @param <D> the type of the data handler
 * @author puce
 */
public abstract class AbstractDataHandlerDescriptor<D> {

    private String icon;
    private Class<D> dataHandlerClass;
    private ResourceLoader resourceLoader;

    /**
     * Creates a new instance of this class.
     */
    public AbstractDataHandlerDescriptor() {
    }

    /**
     * Gets the icon pattern.
     *
     * @return the icon pattern
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the icon pattern.
     *
     * @param icon the icon pattern
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * Gets the type of the Data Handler.
     *
     * @return the type of the Data Handler.
     */
    public Class<D> getDataHandlerClass() {
        return dataHandlerClass;
    }

    /**
     * Sets the type of the Data Handler.
     *
     * @param dataHandlerClass the type of the Data Handler
     */
    public void setDataHandlerClass(Class<D> dataHandlerClass) {
        this.dataHandlerClass = dataHandlerClass;
    }

    /**
     * Gets the resource loader.
     *
     * @return the resource loader
     */
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    /**
     * Sets the resource loader.
     *
     * @param resourceLoader the resource loader
     */
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

}
