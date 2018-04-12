package org.drombler.commons.settings.xml;

import java.io.IOException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.prefs.AbstractPreferences;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeListener;
import javax.xml.bind.JAXBException;
import org.drombler.commons.settings.xml.jaxb.AbstractEntryType;
import org.drombler.commons.settings.xml.jaxb.BooleanEntryType;
import org.drombler.commons.settings.xml.jaxb.ByteArrayEntryType;
import org.drombler.commons.settings.xml.jaxb.DoubleEntryType;
import org.drombler.commons.settings.xml.jaxb.FloatEntryType;
import org.drombler.commons.settings.xml.jaxb.IntEntryType;
import org.drombler.commons.settings.xml.jaxb.LongEntryType;
import org.drombler.commons.settings.xml.jaxb.NodeType;
import org.drombler.commons.settings.xml.jaxb.StringEntryType;

/**
 *
 * @author puce
 */
public class XmlPreferences extends AbstractPreferences {

    private final XmlSettingsStorage storage;
    private final NodeType node;
    private XmlPreferences parent;

    public XmlPreferences(XmlSettingsStorage storage, XmlPreferences parent, String name) {
        super(parent, name);
        this.parent = parent;
        this.storage = storage;
        this.node = new NodeType();
        this.node.setName(name);
    }

    public XmlPreferences(XmlSettingsStorage storage, XmlPreferences parent, NodeType node) {
        super(parent, node.getName());
        this.parent = parent;
        this.storage = storage;
        this.node = node;
    }

    @Override
    protected void putSpi(String key, String value) {
        final StringEntryType entry = new StringEntryType();
        entry.setKey(key);
        entry.setValue(value);
        node.getEntries().add(entry);
    }

    @Override
    protected String getSpi(String key) {
        return getEntry(key, StringEntryType.class)
                .map(StringEntryType::getValue)
                .orElse(null);
    }

    @Override
    public void putInt(String key, int value) {
        checkKey(key);
        final IntEntryType entry = new IntEntryType();
        entry.setKey(key);
        entry.setValue(value);
        node.getEntries().add(entry);
    }

    @Override
    public int getInt(String key, int def) {
        return getEntry(key, IntEntryType.class)
                .map(IntEntryType::getValue)
                .orElse(def);
    }

    @Override
    public void putLong(String key, long value) {
        checkKey(key);
        final LongEntryType entry = new LongEntryType();
        entry.setKey(key);
        entry.setValue(value);
        node.getEntries().add(entry);
    }

    @Override
    public long getLong(String key, long def) {
        return getEntry(key, LongEntryType.class)
                .map(LongEntryType::getValue)
                .orElse(def);
    }

    @Override
    public void putDouble(String key, double value) {
        checkKey(key);
        final DoubleEntryType entry = new DoubleEntryType();
        entry.setKey(key);
        entry.setValue(value);
        node.getEntries().add(entry);
    }

    @Override
    public double getDouble(String key, double def) {
        return getEntry(key, DoubleEntryType.class)
                .map(DoubleEntryType::getValue)
                .orElse(def);
    }

    @Override
    public void putFloat(String key, float value) {
        checkKey(key);
        final FloatEntryType entry = new FloatEntryType();
        entry.setKey(key);
        entry.setValue(value);
        node.getEntries().add(entry);
    }

    @Override
    public float getFloat(String key, float def) {
        return getEntry(key, FloatEntryType.class)
                .map(FloatEntryType::getValue)
                .orElse(def);
    }

    @Override
    public void putBoolean(String key, boolean value) {
        checkKey(key);
        final BooleanEntryType entry = new BooleanEntryType();
        entry.setKey(key);
        entry.setValue(value);
        node.getEntries().add(entry);
    }

    @Override
    public boolean getBoolean(String key, boolean def) {
        return getEntry(key, BooleanEntryType.class)
                .map(BooleanEntryType::isValue)
                .orElse(def);
    }

    @Override
    public void putByteArray(String key, byte[] value) {
        checkKey(key);
        final ByteArrayEntryType entry = new ByteArrayEntryType();
        entry.setKey(key);
        entry.setValue(value);
        node.getEntries().add(entry);
    }

    @Override
    public byte[] getByteArray(String key, byte[] def) {
        return getEntry(key, ByteArrayEntryType.class)
                .map(ByteArrayEntryType::getValue)
                .orElse(def);
    }

    private <T extends AbstractEntryType> Optional<T> getEntry(String key, Class<T> entryType) {
        return node.getEntries().stream()
                .filter(entry -> entry.getKey().equals(key))
                .filter(entryType::isInstance)
                .map(entryType::cast)
                .findFirst();
    }

    @Override
    protected void removeSpi(String key) {
        final Predicate<AbstractEntryType> equalKeyFilter = entry -> entry.getKey().equals(key);
        node.getEntries().removeIf(equalKeyFilter);
    }

    @Override
    protected void removeNodeSpi() throws BackingStoreException {
        parent.node.getChildren().remove(this.node);
    }

    @Override
    protected String[] keysSpi() throws BackingStoreException {
        return node.getEntries().stream()
                .map(AbstractEntryType::getKey)
                .toArray(String[]::new);
    }

    @Override
    protected String[] childrenNamesSpi() throws BackingStoreException {
        return node.getChildren().stream()
                .map(NodeType::getName)
                .toArray(String[]::new);
    }

    @Override
    protected XmlPreferences childSpi(String name) {
        return node.getChildren().stream()
                .filter(child -> child.getName().equals(name))
                .findFirst()
                .map(child -> new XmlPreferences(storage, this, child))
                .orElse(new XmlPreferences(storage, this, name));
    }

    @Override
    protected void syncSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not supported. Use sync.");
    }

    @Override
    public void sync() throws BackingStoreException {
        flush();
    }

    @Override
    protected void flushSpi() throws BackingStoreException {
        throw new UnsupportedOperationException("Not supported. Use flush.");
    }

    @Override
    public void flush() throws BackingStoreException {
        try {
            storage.store();
        } catch (JAXBException | IOException ex) {
            throw new BackingStoreException(ex);
        }
    }

    @Override
    public void addNodeChangeListener(NodeChangeListener ncl) {
        super.addNodeChangeListener(ncl); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addPreferenceChangeListener(PreferenceChangeListener pcl) {
        super.addPreferenceChangeListener(pcl); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeNodeChangeListener(NodeChangeListener ncl) {
        super.removeNodeChangeListener(ncl); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removePreferenceChangeListener(PreferenceChangeListener pcl) {
        super.removePreferenceChangeListener(pcl); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isUserNode() {
        return super.isUserNode(); //To change body of generated methods, choose Tools | Templates.
    }

    private void checkKey(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
