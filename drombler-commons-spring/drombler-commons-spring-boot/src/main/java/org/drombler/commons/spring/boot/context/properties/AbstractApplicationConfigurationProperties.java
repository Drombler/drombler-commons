package org.drombler.commons.spring.boot.context.properties;

import javax.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author puce
 */
public class AbstractApplicationConfigurationProperties implements ApplicationConfigurationProperties{
    @NotBlank
    private String name;

    @NestedConfigurationProperty
    private final ContactConfigurationProperties contact = new ContactConfigurationProperties();

    @Bean
    @Override
    public ContactConfigurationProperties getContact() {
        return contact;
    }
    
    
}
