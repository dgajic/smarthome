package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import java.util.Optional;

import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;

public class Ds2438TypeAdapter extends SensorTypeAdapter<Ds2438MultiSensor> {

    @Override
    protected Ds2438MultiSensor create() {
        return new Ds2438MultiSensor();
    }

    @Override
    protected void registerAdditionalHandlers() {
        super.registerAdditionalHandlers();

        registerHandler(TEMPERATURE, (dev, reader) -> {
            dev.getValue().set(TEMPERATURE, Optional.ofNullable(reader.nextDouble()).orElse(null));
        });

        registerHandler(HUMIDITY, (dev, reader) -> {
            dev.getValue().set(HUMIDITY, Optional.ofNullable(reader.nextDouble()).orElse(null));
        });

        registerHandler(ILLUMINANCE, (dev, reader) -> {
            dev.getValue().set(ILLUMINANCE, Optional.ofNullable(reader.nextDouble()).orElse(null));
        });
    }

}
