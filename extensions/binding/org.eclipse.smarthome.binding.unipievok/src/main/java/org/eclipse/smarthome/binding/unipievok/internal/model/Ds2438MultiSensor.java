package org.eclipse.smarthome.binding.unipievok.internal.model;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ds2438MultiSensor extends MultiSensor {

    private static final Set<String> FIELDS = new HashSet<>(Arrays.asList(TEMPERATURE, HUMIDITY, ILLUMINANCE));

    public Ds2438MultiSensor() {
        // @formatter:off
        MultiValue value = new MultiValue.Builder()
                .declare(TEMPERATURE, Double.class)
                .declare(HUMIDITY, Double.class)
                .declare(ILLUMINANCE, Double.class)
                .build();
        // @formatter:on
        this.setValue(value);
    }

    @Override
    public Set<String> supportedFields() {
        return FIELDS;
    }

    public Double getTemperature() {
        return getValue().get(TEMPERATURE, Double.class);
    }

    public Double getHumidity() {
        return getValue().get(HUMIDITY, Double.class);
    }

    public Double getIlluminance() {
        return getValue().get(ILLUMINANCE, Double.class);
    }
}
