package org.power.configuration.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.power.configuration.event.ConfigurationChangeEvent;
import org.power.configuration.event.ConfigurationItemDeletedEvent;
import org.power.configuration.event.ConfigurationReloadedEvent;

public class PropertiesFileConfiguration extends FileConfiguration {

    protected Properties properties;

    public PropertiesFileConfiguration(String pathname) throws IOException {
        this(new File(pathname));
    }

    public PropertiesFileConfiguration(File file) throws IOException {
        super(file);
        this.init();
    }

    protected void init() throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            properties = new Properties();
            properties.load(is);
        }
    }

    @Override
    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public String set(String key, String value) {
        Object oval = properties.setProperty(key, value);
        eventEmitter.emit(new ConfigurationChangeEvent(this, key, oval, value));
        return (oval instanceof String) ? (String) oval : null;
    }

    @Override
    public boolean delete(String key) {
        boolean result = properties.containsKey(key);
        properties.remove(key);
        if (result) {
            eventEmitter.emit(new ConfigurationItemDeletedEvent(this, key));
        }
        return result;
    }

    @Override
    public boolean delete(String key, String value) {
        boolean result = properties.remove(key, value);
        if (result) {
            eventEmitter.emit(new ConfigurationItemDeletedEvent(this, key));
        }
        return result;
    }

    @Override
    public String save(String key, String value) throws IOException {
        Object oval = properties.setProperty(key, value);
        eventEmitter.emit(new ConfigurationChangeEvent(this, key, oval, value));
        this.store("update " + key);
        return (oval instanceof String) ? (String) oval : null;
    }

    @Override
    public void reload() throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            eventEmitter.error(e);
            throw e;
        }
        eventEmitter.emit(new ConfigurationReloadedEvent(this));
    }

    protected void store(String comment) throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, comment);
        } catch (IOException e) {
            eventEmitter.error(e);
            throw e;
        }
    }

}
