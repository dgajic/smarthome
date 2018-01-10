package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MultiBinaryDevices<T extends BinaryDevice> extends Device {
    private final Map<String, T> devices = new ConcurrentHashMap<>();

    public MultiBinaryDevices(String id, Map<String, String> properties) {
        super(id, properties);
    }

    public MultiBinaryDevices(String id) {
        super(id);
    }

    public Collection<T> getDevices() {
        return Collections.unmodifiableCollection(devices.values());
    }

    public void addDevice(T device) {
        devices.put(device.getId(), device);
    }

    public void removeDevice(String id) {
        devices.remove(id);
    }

    private T getOrThrow(String id) {
        T device = devices.get(id);
        if (device != null) {
            return device;
        } else {
            throw new IllegalArgumentException("Non-existing device with id " + id);
        }
    }

    public boolean isSet(String id) {
        return getOrThrow(id).isSet();
    }

    public void set(String id) {
        getOrThrow(id).set();
    }

    public void set(String id, boolean state) {
        getOrThrow(id).set(state);
    }

    public void reset(String id) {
        getOrThrow(id).reset();
    }
}
