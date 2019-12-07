package org.drombler.commons.spring.boot.context.properties;

import java.net.URL;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The contact configuration properties.
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
     * Gets the name of the software provider of this application.
     *
     * @return the name of the software provider of this application
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the software provider of this application.
     *
     * @param name the name of the software provider of this application
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the homepage of the software provider of this application.
     *
     * @return the homepage of the software provider of this application
     */
    public URL getHomepage() {
        return homepage;
    }

    /**
     * Sets the homepage of the software provider of this application.
     *
     * @param homepage the homepage of the software provider of this application
     */
    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }

    /**
     * Gets the email address of the software provider of this application.
     *
     * @return the email address of the software provider of this application
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the software provider of this application.
     *
     * @param email the email address of the software provider of this application
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
