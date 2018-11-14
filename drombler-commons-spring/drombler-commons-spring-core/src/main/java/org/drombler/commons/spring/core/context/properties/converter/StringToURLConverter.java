package org.drombler.commons.spring.core.context.properties.converter;

import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ConfigurationPropertiesBinding
public class StringToURLConverter implements Converter<String, URL> {

    /**
     * {@inheritDoc }
     */
    @Override
    public URL convert(String urlString) {
        if (urlString == null) {
            return null;
        }
        try {
            return new URL(urlString);
        } catch (MalformedURLException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

}
