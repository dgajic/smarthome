package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.HashMap;
import java.util.Map;

public abstract class Device {
    private String id;
    private int globDevId;
    private final Map<String, Object> properties;

    public Device() {
        this.properties = new HashMap<>();
    }

    public Device(String id) {
        this();
        this.id = id;
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

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperty(String key, Object value) {
        properties.put(key, value);
    }

    public Object getProperty(String key) {
        return properties.get(key);
    }

    public int getGlobDevId() {
        return globDevId;
    }

    public void setGlobDevId(int globDevId) {
        this.globDevId = globDevId;
    }

}
