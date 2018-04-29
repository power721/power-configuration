package org.power.configuration.event;

public class ConfigurationChangeEvent extends ConfigurationEvent {

    private final String key;
    private final Object oldValue;
    private final Object newValue;

    public ConfigurationChangeEvent(Object source, String key, Object oldValue, Object newValue) {
        super(source);
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String getKey() {
        return key;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    @Override
    public String toString() {
        return "ConfigurationChangeEvent{" +
            "key='" + key + '\'' +
            ", oldValue=" + oldValue +
            ", newValue=" + newValue +
            ", source=" + source +
            '}';
    }
}
