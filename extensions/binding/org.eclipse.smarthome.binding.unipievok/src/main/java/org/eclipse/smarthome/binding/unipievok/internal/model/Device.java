package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Device {
    private String id;
    private final Map<String, String> properties;

    public Device(String id) {
        this.id = id;
        this.properties = new HashMap<>();
    }

    public Device(String id, Map<String, String> properties) {
        this.id = id;
        this.properties = new HashMap<>(properties);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperty(String key, String value) {
        properties.put(key, value);
    }
}
