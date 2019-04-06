package org.drombler.commons.spring.boot.context.properties;

import java.net.URI;
import java.time.Duration;

/**
 * The typical base properties required to integrate a remote service.
 */
public interface IntegrationConfigurationProperties {

    /**
     * Gets the endpoint of the remote service to integrate.
     *
     * @return the endpoint of the remote service to integrate
     */
    URI getEndpoint();

    /**
     * Gets the connect timeout.
     *
     * @return the connect timeout
     */
    Duration getConnectTimeout();

    /**
     * Gets the read timeout.
     *
     * @return the read timeout
     */
    Duration getReadTimeout();
}
