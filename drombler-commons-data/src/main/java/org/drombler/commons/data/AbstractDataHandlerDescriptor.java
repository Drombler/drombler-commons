package org.drombler.commons.data;

import org.softsmithy.lib.util.ResourceLoader;
/**
 *
 * @author puce
 * @param <D> the type of the data handler
 */
public abstract class AbstractDataHandlerDescriptor<D> {

    private String icon;
    private Class<D> dataHandlerClass;
    private ResourceLoader resourceLoader;

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Class<D> getDataHandlerClass() {
        return dataHandlerClass;
    }

    /**
     * @param dataHandlerClass the dataHandlerClass to set
     */
    public void setDataHandlerClass(Class<D> dataHandlerClass) {
        this.dataHandlerClass = dataHandlerClass;
    }

    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


}
