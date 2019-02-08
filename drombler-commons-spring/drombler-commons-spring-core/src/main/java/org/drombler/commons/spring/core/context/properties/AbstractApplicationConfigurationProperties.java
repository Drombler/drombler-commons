package org.drombler.commons.spring.core.context.properties;

import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;

/**
 *
 * @author puce
 */
public class AbstractApplicationConfigurationProperties implements ApplicationConfigurationProperties{
    @NotNull
    private String name;
    @NestedConfigurationProperty
    private final ContactConfigurationProperties contact = new ContactConfigurationProperties();

    @Bean
    @Override
    public ContactConfigurationProperties getContact() {
        return contact;
    }
    
    
}
