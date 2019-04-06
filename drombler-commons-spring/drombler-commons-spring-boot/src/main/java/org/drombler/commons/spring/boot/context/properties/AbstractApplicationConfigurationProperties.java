package org.drombler.commons.spring.boot.context.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;

/**
 * A base class for application configuration properties.
 *
 * @see ConfigurationProperties
 */
public class AbstractApplicationConfigurationProperties implements ApplicationConfigurationProperties {

//    @NotBlank
//    private String name;

    @NestedConfigurationProperty
    private final ContactConfigurationProperties contact = new ContactConfigurationProperties();

    /**
     * {@inheritDoc }
     */
    @Bean
    @Override
    public ContactConfigurationProperties getContact() {
        return contact;
    }

}
