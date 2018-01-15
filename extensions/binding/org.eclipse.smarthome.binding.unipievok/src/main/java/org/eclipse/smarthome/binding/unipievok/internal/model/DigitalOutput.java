package org.eclipse.smarthome.binding.unipievok.internal.model;

public class DigitalOutput extends BinaryDevice {
    private boolean pending;

    public boolean isPending() {
        return pending;
    }

    public void setPending(boolean pending) {
        this.pending = pending;
    }
}
