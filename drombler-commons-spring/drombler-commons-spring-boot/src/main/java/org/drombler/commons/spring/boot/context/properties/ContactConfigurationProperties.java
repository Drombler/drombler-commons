package org.drombler.commons.spring.boot.context.properties;

import java.net.URL;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author puce
 */
public class ContactConfigurationProperties {

    @NotBlank
    private String name;

    @NotNull
    private URL homepage;

    @NotBlank
    @Email
    private String email;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the homepage
     */
    public URL getHomepage() {
        return homepage;
    }

    /**
     * @param homepage the homepage to set
     */
    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
