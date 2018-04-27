package org.power.configuration;

import java.io.IOException;

public interface Configuration {

    String get(String key);

    String get(String key, String defaultValue);

    Integer getInt(String key);

    Integer getInt(String key, Integer defaultValue);

    Long getLong(String key);

    Long getLong(String key, Long defaultValue);

    Double getDouble(String key);

    Double getDouble(String key, Double defaultValue);

    Boolean getBoolean(String key);

    Boolean getBoolean(String key, Boolean defaultValue);

    String set(String key, String value);

    Integer set(String key, Integer value);

    Long set(String key, Long value);

    Double set(String key, Double value);

    Boolean set(String key, Boolean value);

    String save(String key, String value) throws IOException;

    Integer save(String key, Integer value) throws IOException;

    Long save(String key, Long value) throws IOException;

    Double save(String key, Double value) throws IOException;

    Boolean save(String key, Boolean value) throws IOException;

    void reload() throws IOException;

    void save() throws IOException;

}
