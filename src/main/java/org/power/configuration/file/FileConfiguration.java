package org.power.configuration.file;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import org.power.configuration.Configuration;

public abstract class FileConfiguration implements Configuration {

    protected File file;

    @Override
    public String get(String key) {
        return get(key, null);
    }

    @Override
    public Integer save(String key, Integer value) throws IOException {
        Integer oval = getInt(key);
        save(key, Objects.toString(value));
        this.store("update " + key);
        return oval;
    }

    @Override
    public Long save(String key, Long value) throws IOException {
        Long oval = getLong(key);
        save(key, Objects.toString(value));
        this.store("update " + key);
        return oval;
    }

    @Override
    public Double save(String key, Double value) throws IOException {
        Double oval = getDouble(key);
        save(key, Objects.toString(value));
        this.store("update " + key);
        return oval;
    }

    @Override
    public Boolean save(String key, Boolean value) throws IOException {
        Boolean oval = getBoolean(key);
        save(key, Objects.toString(value));
        this.store("update " + key);
        return oval;
    }

    @Override
    public void save() throws IOException {
        this.store("save all properties");
    }

    protected abstract void store(String comment) throws IOException;

}