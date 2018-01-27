package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.util.Optional;

import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;

public class Ds18b20TypeAdapter extends SensorTypeAdapter<TemperatureSensor> {

    @Override
    protected TemperatureSensor create() {
        return new TemperatureSensor();
    }

    @Override
    protected void registerAdditionalHandlers() {
        super.registerAdditionalHandlers();

        registerHandler("value", (dev, reader) -> {
            dev.setValue(Optional.ofNullable(reader.nextDouble()).orElse(null));
        });
    }
}
