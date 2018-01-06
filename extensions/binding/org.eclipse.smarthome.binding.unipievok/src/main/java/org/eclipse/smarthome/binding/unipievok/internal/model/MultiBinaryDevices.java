package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MultiBinaryDevices extends Device {
    private final Map<String, BinaryDevice> devices = new ConcurrentHashMap<>();

    public MultiBinaryDevices(String id, Map<String, String> properties) {
        super(id, properties);
    }

    public MultiBinaryDevices(String id) {
        super(id);
    }

    public Collection<BinaryDevice> getDevices() {
        return Collections.unmodifiableCollection(devices.values());
    }

    public void addDevice(BinaryDevice device) {
        devices.put(device.getId(), device);
    }

    public void removeDevice(String id) {
        devices.remove(id);
    }

    private BinaryDevice getOrThrow(String id) {
        BinaryDevice device = devices.get(id);
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
