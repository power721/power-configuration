package org.power.configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertiesFileConfiguration implements Configuration {

    private File file;
    private Properties properties;

    public PropertiesFileConfiguration(String pathname) throws IOException {
        this(new File(pathname));
    }

    public PropertiesFileConfiguration(File file) throws IOException {
        this.file = file;
        try (InputStream is = new FileInputStream(file)) {
            properties = new Properties();
            properties.load(is);
        }
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    @Override
    public Integer getInt(String key) {
        return getInt(key, null);
    }

    @Override
    public Integer getInt(String key, Integer defaultValue) {
        String value = properties.getProperty(key);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // ignore
        }

        return defaultValue;
    }

    @Override
    public Long getLong(String key) {
        return getLong(key, null);
    }

    @Override
    public Long getLong(String key, Long defaultValue) {
        String value = properties.getProperty(key);

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            // ignore
        }

        return defaultValue;
    }

    @Override
    public Double getDouble(String key) {
        return getDouble(key, null);
    }

    @Override
    public Double getDouble(String key, Double defaultValue) {
        String value = properties.getProperty(key);

        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        return defaultValue;
    }

    @Override
    public Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    @Override
    public Boolean getBoolean(String key, Boolean defaultValue) {
        String value = properties.getProperty(key);

        if (value != null) {
            if ("true".equalsIgnoreCase(value)) {
                return true;
            } else if ("false".equalsIgnoreCase(value)) {
                return false;
            }
        }

        return defaultValue;
    }

    public String set(String key, String value) {
        Object oval = properties.setProperty(key, value);
        return (oval instanceof String) ? (String) oval : null;
    }

    @Override
    public Integer set(String key, Integer value) {
        Integer oval = getInt(key);
        properties.setProperty(key, Objects.toString(value));
        return oval;
    }

    @Override
    public Long set(String key, Long value) {
        Long oval = getLong(key);
        properties.setProperty(key, Objects.toString(value));
        return oval;
    }

    @Override
    public Double set(String key, Double value) {
        Double oval = getDouble(key);
        properties.setProperty(key, Objects.toString(value));
        return oval;
    }

    @Override
    public Boolean set(String key, Boolean value) {
        Boolean oval = getBoolean(key);
        properties.setProperty(key, Objects.toString(value));
        return oval;
    }

    public String save(String key, String value) throws IOException {
        Object oval = properties.setProperty(key, value);
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "update " + key);
        }
        return (oval instanceof String) ? (String) oval : null;
    }

    @Override
    public Integer save(String key, Integer value) throws IOException {
        Integer oval = getInt(key);
        properties.setProperty(key, Objects.toString(value));
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "update " + key);
        }
        return oval;
    }

    @Override
    public Long save(String key, Long value) throws IOException {
        Long oval = getLong(key);
        properties.setProperty(key, Objects.toString(value));
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "update " + key);
        }
        return oval;
    }

    @Override
    public Double save(String key, Double value) throws IOException {
        Double oval = getDouble(key);
        properties.setProperty(key, Objects.toString(value));
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "update " + key);
        }
        return oval;
    }

    @Override
    public Boolean save(String key, Boolean value) throws IOException {
        Boolean oval = getBoolean(key);
        properties.setProperty(key, Objects.toString(value));
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "update " + key);
        }
        return oval;
    }

    public void reload() throws IOException {
        try (InputStream is = new FileInputStream(file)) {
            properties = new Properties();
            properties.load(is);
        }
    }

    public void save() throws IOException {
        try (OutputStream out = new FileOutputStream(file)) {
            properties.store(out, "save all properties");
        }
    }

}
