package org.drombler.commons.settings.xml;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.drombler.commons.settings.SettingsStorage;
import org.drombler.commons.settings.xml.jaxb.Settings2Type;

/**
 *
 * @author puce
 */
public class XmlSettingsStorage implements SettingsStorage {

    private final Path settingsFilePath;
    private final JAXBContext context;

    private Settings2Type settings;

    public XmlSettingsStorage(Path settingsFilePath) throws JAXBException {
        this.settingsFilePath = settingsFilePath;
        context = JAXBContext.newInstance(Settings2Type.class);
    }

    public void load() throws JAXBException, IOException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        try (InputStream is = Files.newInputStream(settingsFilePath);
                BufferedInputStream bis = new BufferedInputStream(is)) {
            this.settings = (Settings2Type) unmarshaller.unmarshal(bis);
        }
    }

    public void store() throws JAXBException, IOException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        try (OutputStream os = Files.newOutputStream(settingsFilePath);
                BufferedOutputStream bos = new BufferedOutputStream(os)) {
            marshaller.marshal(getSettings(), bos);
        }
    }

    public Settings2Type getSettings() {
        if (settings == null) {
            settings = new Settings2Type();
        }
        return settings;
    }
}
