package org.drombler.commons.settings;

/**
 *
 * @author puce
 */
public interface SettingsStorage {

    void load() throws Exception;

    void store() throws Exception;

}
