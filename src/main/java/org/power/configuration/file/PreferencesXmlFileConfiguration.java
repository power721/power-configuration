package org.power.configuration.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;
import java.util.prefs.BackingStoreException;
import java.util.prefs.InvalidPreferencesFormatException;
import java.util.prefs.Preferences;
import org.power.configuration.event.ConfigurationChangeEvent;
import org.power.configuration.event.ConfigurationInitializedEvent;
import org.power.configuration.event.ConfigurationReloadedEvent;

public class PreferencesXmlFileConfiguration extends FileConfiguration {

    private final Preferences preferences;

    public PreferencesXmlFileConfiguration(String pathname) throws IOException {
        this(new File(pathname));
    }

    public PreferencesXmlFileConfiguration(File file) throws IOException {
        super(file);
        preferences = Preferences.userRoot();
        try {
            Preferences.importPreferences(new FileInputStream(file));
        } catch (InvalidPreferencesFormatException e) {
            throw new IOException("Cannot parse Preferences file.", e);
        }
        eventEmitter.emit(new ConfigurationInitializedEvent(this));
    }

    @Override
    public String get(String key, String defaultValue) {
        return getNode(key).get(getName(key), defaultValue);
    }

    @Override
    public String set(String key, String value) {
        Preferences node = getNode(key);
        String old = node.get(getName(key), null);
        node.put(getName(key), value);
        eventEmitter.emit(new ConfigurationChangeEvent(this, key, old, value));
        return old;
    }

    @Override
    public boolean delete(String key) {
        Preferences node = getNode(key);
        node.remove(getName(key));
        return true;
    }

    @Override
    public boolean delete(String key, String value) {
        Preferences node = getNode(key);
        String old = node.get(getName(key), null);
        if (Objects.equals(old, value)) {
            node.remove(getName(key));
            return true;
        }
        return false;
    }

    @Override
    public String save(String key, String value) throws IOException {
        String old = set(key, value);
        store(null);
        return old;
    }

    @Override
    protected void store(String comment) throws IOException {
        try (OutputStream os = new FileOutputStream(file)) {
            preferences.exportSubtree(os);
        } catch (BackingStoreException e) {
            eventEmitter.error(e);
            throw new IOException("Cannot write preferences to file.", e);
        } catch (IOException e) {
            eventEmitter.error(e);
            throw e;
        }
    }

    @Override
    public void reload() throws IOException {
        try {
            Preferences.importPreferences(new FileInputStream(file));
        } catch (InvalidPreferencesFormatException e) {
            eventEmitter.error(e);
            throw new IOException("Cannot parse Preferences file.", e);
        } catch (IOException e) {
            eventEmitter.error(e);
            throw e;
        }
        eventEmitter.emit(new ConfigurationReloadedEvent(this));
    }

    private Preferences getNode(String key) {
        Objects.requireNonNull(key, "The key cannot be empty.");
        String[] paths = key.split("[./]");
        if (paths.length > 1) {
            String[] node = new String[paths.length - 1];
            System.arraycopy(paths, 0, node, 0, paths.length - 1);
            return preferences.node(String.join("/", node));
        }
        return preferences;
    }

    private String getName(String key) {
        String[] paths = key.split("[./]");
        return paths[paths.length - 1];
    }

}
