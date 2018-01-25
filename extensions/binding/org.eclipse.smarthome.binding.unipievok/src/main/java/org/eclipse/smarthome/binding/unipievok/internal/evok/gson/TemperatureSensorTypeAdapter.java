package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;

public class TemperatureSensorTypeAdapter extends SensorTypeAdapter<Double, TemperatureSensor> {

    @Override
    protected TemperatureSensor create() {
        return new TemperatureSensor();
    }

    @Override
    protected Double asT(String val) {
        return val == null ? null : Double.valueOf(val);
    }
}
