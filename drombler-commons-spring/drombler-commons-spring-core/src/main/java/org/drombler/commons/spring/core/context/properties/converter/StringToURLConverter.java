package org.drombler.commons.spring.core.context.properties.converter;

import java.net.URL;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;

/**
 *
 * @author puce
 */
@ConfigurationPropertiesBinding
public class StringToURLConverter implements Converter<String, URL> {

    @Override
    public URL convert(String urlString) {
        if (urlString == null) {
            return null;
        }
        return new URL(urlString);
    }

}
