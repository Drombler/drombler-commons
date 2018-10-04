package org.drombler.commons.spring.core.context.properties;


import java.net.URL;
import javax.validation.constraints.NotNull;

/**
 *
 * @author puce
 */
public abstract class AbstractIntegrationConfigurationProperties {


    @NotNull
    private URL endpoint;
    private int connectTimeout = 20000;
    private int readTimeout = 70000;

    /**
     * @return the endpoint
     */
    public URL getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(URL endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return the connectTimeout
     */
    public int getConnectTimeout() {
        return connectTimeout;
    }

    /**
     * @param connectTimeout the connectTimeout to set
     */
    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    /**
     * @return the readTimeout
     */
    public int getReadTimeout() {
        return readTimeout;
    }

    /**
     * @param readTimeout the readTimeout to set
     */
    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }
}
