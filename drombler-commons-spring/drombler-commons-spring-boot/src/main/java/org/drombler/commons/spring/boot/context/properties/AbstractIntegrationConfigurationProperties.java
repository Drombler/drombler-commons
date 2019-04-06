package org.drombler.commons.spring.boot.context.properties;


import java.net.URI;
import java.time.Duration;
import javax.validation.constraints.NotNull;

/**
 *
 * @author puce
 */
public abstract class AbstractIntegrationConfigurationProperties implements IntegrationConfigurationProperties {


    @NotNull
    private URI endpoint;
    private Duration connectTimeout = Duration.ofSeconds(20);
    private Duration readTimeout = Duration.ofSeconds(70);

    /**
     * @return the endpoint
     */
    @Override
    public URI getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return the connectTimeout
     */
    @Override
    public Duration getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param connectTimeout the connectTimeout to set
     */
    public void setConnectTimeout(Duration connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * @return the readTimeout
     */
    @Override
    public Duration getReadTimeout() {
        return readTimeout;
    }

    /**
     * @param readTimeout the readTimeout to set
     */
    public void setReadTimeout(Duration readTimeout) {
        this.readTimeout = readTimeout;
    }
}
