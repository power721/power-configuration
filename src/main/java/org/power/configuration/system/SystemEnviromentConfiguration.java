package org.power.configuration.system;

import java.io.IOException;
import org.power.configuration.Configuration;

public class SystemEnviromentConfiguration implements Configuration {

    @Override
    public String get(String key) {
        return System.getenv(key);
    }

    @Override
    public String get(String key, String defaultValue) {
        String value = System.getenv(key);
        return value == null ? defaultValue : value;
    }

    @Override
    public String set(String key, String value) {
        return null;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public boolean delete(String key, String value) {
        return false;
    }

    @Override
    public String save(String key, String value) throws IOException {
        return null;
    }

    @Override
    public Integer save(String key, Integer value) throws IOException {
        return null;
    }

    @Override
    public Long save(String key, Long value) throws IOException {
        return null;
    }

    @Override
    public Double save(String key, Double value) throws IOException {
        return null;
    }

    @Override
    public Boolean save(String key, Boolean value) throws IOException {
        return null;
    }

    @Override
    public void reload() throws IOException {

    }

    @Override
    public void save() throws IOException {

    }
}
