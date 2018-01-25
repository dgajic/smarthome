package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.time.LocalDateTime;

public class Sensor<T> extends Device {
    private T value;
    private LocalDateTime time;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
