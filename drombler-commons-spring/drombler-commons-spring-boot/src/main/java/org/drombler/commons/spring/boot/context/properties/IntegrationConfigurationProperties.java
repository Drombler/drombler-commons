package org.drombler.commons.spring.boot.context.properties;

import java.net.URI;
import java.time.Duration;

/**
 * The typical base properties required to integrate a remote service.
 */
public interface IntegrationConfigurationProperties {

    /**
     * @return the endpoint
     */
    URI getEndpoint();

    /**
     * @return the connectTimeout
     */
    Duration getConnectTimeout();

    /**
     * @return the readTimeout
     */
    Duration getReadTimeout();
}
