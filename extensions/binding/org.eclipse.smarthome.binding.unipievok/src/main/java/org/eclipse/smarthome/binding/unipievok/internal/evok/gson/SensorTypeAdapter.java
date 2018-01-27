package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;

public abstract class SensorTypeAdapter<S extends Sensor<?>> extends DeviceTypeAdapter<S> {

    @Override
    protected abstract S create();

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("time", (dev, reader) -> {
            Double t = reader.nextDouble();
            dev.setTime(LocalDateTime.ofEpochSecond(t.longValue(), 0, ZoneOffset.UTC));
        });
    }
}
