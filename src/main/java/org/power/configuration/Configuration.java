package org.power.configuration;

import java.io.IOException;
import java.util.Objects;

public interface Configuration {

    String get(String key);

    String get(String key, String defaultValue);

    default Integer getInt(String key) {
        return getInt(key, null);
    }

    default Integer getInt(String key, Integer defaultValue) {
        String value = get(key);

        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            // ignore
        }

        return defaultValue;
    }

    default Long getLong(String key) {
        return getLong(key, null);
    }

    default Long getLong(String key, Long defaultValue) {
        String value = get(key);

        try {
            return Long.parseLong(value);
        } catch (NumberFormatException e) {
            // ignore
        }

        return defaultValue;
    }

    default Double getDouble(String key) {
        return getDouble(key, null);
    }

    default Double getDouble(String key, Double defaultValue) {
        String value = get(key);

        if (value != null) {
            try {
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                // ignore
            }
        }

        return defaultValue;
    }

    default Boolean getBoolean(String key) {
        return getBoolean(key, null);
    }

    default Boolean getBoolean(String key, Boolean defaultValue) {
        String value = get(key);

        if (value != null) {
            if ("true".equalsIgnoreCase(value)) {
                return true;
            } else if ("false".equalsIgnoreCase(value)) {
                return false;
            }
        }

        return defaultValue;
    }

    String set(String key, String value);

    default Integer set(String key, Integer value) {
        Integer oval = getInt(key);
        set(key, Objects.toString(value));
        return oval;
    }

    default Long set(String key, Long value) {
        Long oval = getLong(key);
        set(key, Objects.toString(value));
        return oval;
    }

    default Double set(String key, Double value) {
        Double oval = getDouble(key);
        set(key, Objects.toString(value));
        return oval;
    }

    default Boolean set(String key, Boolean value) {
        Boolean oval = getBoolean(key);
        set(key, Objects.toString(value));
        return oval;
    }

    boolean delete(String key);

    boolean delete(String key, String value);

    default boolean delete(String key, Integer value) {
        return delete(key, Objects.toString(value));
    }

    default boolean delete(String key, Long value) {
        return delete(key, Objects.toString(value));
    }

    default boolean delete(String key, Double value) {
        return delete(key, Objects.toString(value));
    }

    default boolean delete(String key, Boolean value) {
        return delete(key, Objects.toString(value));
    }

    String save(String key, String value) throws IOException;

    Integer save(String key, Integer value) throws IOException;

    Long save(String key, Long value) throws IOException;

    Double save(String key, Double value) throws IOException;

    Boolean save(String key, Boolean value) throws IOException;

    void reload() throws IOException;

    void save() throws IOException;

}
