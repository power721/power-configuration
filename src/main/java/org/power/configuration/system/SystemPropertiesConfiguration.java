package org.power.configuration.system;

import java.io.IOException;
import java.util.Objects;
import org.power.configuration.Configuration;

public class SystemPropertiesConfiguration implements Configuration {

    @Override
    public String get(String key) {
        return System.getProperty(key);
    }

    @Override
    public String get(String key, String defaultValue) {
        return System.getProperty(key, defaultValue);
    }

    @Override
    public String set(String key, String value) {
        return System.setProperty(key, value);
    }

    @Override
    public boolean delete(String key) {
        System.clearProperty(key);
        return true;
    }

    @Override
    public boolean delete(String key, String value) {
        String old = System.getProperty(key);
        if (Objects.equals(old, value)) {
            System.clearProperty(key);
            return true;
        }
        return false;
    }

    @Override
    public String save(String key, String value) throws IOException {
        return System.setProperty(key, value);
    }

    @Override
    public Integer save(String key, Integer value) throws IOException {
        Integer old = getInt(key);
        System.setProperty(key, Objects.toString(value));
        return old;
    }

    @Override
    public Long save(String key, Long value) throws IOException {
        Long old = getLong(key);
        System.setProperty(key, Objects.toString(value));
        return old;
    }

    @Override
    public Double save(String key, Double value) throws IOException {
        Double old = getDouble(key);
        System.setProperty(key, Objects.toString(value));
        return old;
    }

    @Override
    public Boolean save(String key, Boolean value) throws IOException {
        Boolean old = getBoolean(key);
        System.setProperty(key, Objects.toString(value));
        return old;
    }

    @Override
    public void reload() throws IOException {

    }

    @Override
    public void save() throws IOException {

    }
}
