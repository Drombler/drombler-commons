package org.drombler.commons.spring.core.context.properties.converter;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
@ConfigurationPropertiesBinding
public class StringToURIConverter implements Converter<String, URI> {

    /**
     * {@inheritDoc }
     */
    @Override
    public URI convert(String urlString) {
        if (urlString == null) {
            return null;
        }
        try {
            return new URI(urlString);
        } catch (URISyntaxException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

}
