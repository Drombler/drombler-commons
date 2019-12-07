package org.drombler.commons.spring.boot.context.properties;

/**
 * The typical base properties for applications.
 */
public interface ApplicationConfigurationProperties {

    /**
     * Gets the contact details of the software provider of this application.
     *
     * @return the contact details of the software provider of this application
     */
    ContactConfigurationProperties getContact();
}
