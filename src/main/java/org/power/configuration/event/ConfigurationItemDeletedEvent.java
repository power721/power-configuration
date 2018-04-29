package org.power.configuration.event;

public class ConfigurationItemDeletedEvent extends ConfigurationEvent {

    private final String key;

    public ConfigurationItemDeletedEvent(Object source, String key) {
        super(source);
        this.key = key;
    }

    @Override
    public String toString() {
        return "ConfigurationItemDeletedEvent{" +
            "key='" + key + '\'' +
            ", source=" + source +
            '}';
    }

}
