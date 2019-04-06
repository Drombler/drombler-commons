package org.drombler.commons.spring.boot.context.properties;

import java.net.URI;
import java.time.Duration;
import javax.validation.constraints.NotNull;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * A base class for remote service integration configuration properties.
 *
 * @see ConfigurationProperties
 */
public abstract class AbstractIntegrationConfigurationProperties implements IntegrationConfigurationProperties {

    @NotNull
    private URI endpoint;
    private Duration connectTimeout = Duration.ofSeconds(20);
    private Duration readTimeout = Duration.ofSeconds(70);

    /**
     * {@inheritDoc}
     */
    @Override
    public URI getEndpoint() {
        return endpoint;
    }

    /**
     * Sets the endpoint of the remote service to integrate.
     *
     * @param endpoint the endpoint of the remote service to integrate
     */
    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * Sets the connect timeout.
     *
     * @param connectTimeout the connect timeout
     */
    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Duration getReadTimeout() {
        return readTimeout;
    }

    /**
     * Sets the read timeout.
     *
     * @param readTimeout the read timeout
     */
    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }
}
