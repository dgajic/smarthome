package org.eclipse.smarthome.binding.unipievok.internal.model;

public abstract class BinaryDevice extends Device {

    private boolean state;

    public BinaryDevice() {
        super();
    }

    public BinaryDevice(String id, boolean state) {
        super(id);
        this.state = state;
    }

    public boolean isSet() {
        return state;
    }

    public void set() {
        state = true;
    }

    public void set(boolean state) {
        this.state = state;
    }

    public void reset() {
        state = false;
    }
}
