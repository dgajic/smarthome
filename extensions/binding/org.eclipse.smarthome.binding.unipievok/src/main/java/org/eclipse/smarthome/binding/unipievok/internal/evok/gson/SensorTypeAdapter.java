package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;

import com.google.gson.stream.JsonReader;

public abstract class SensorTypeAdapter<T, S extends Sensor<T>> extends DeviceTypeAdapter<S> {

    @Override
    protected abstract S create();

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, S sen) throws IOException {
        boolean set = true;
        switch (name) {
            case "value":
                sen.setValue(asT(reader.nextString()));
                break;
            case "circuit":
                sen.setId(reader.nextString());
                break;
            case "time":
                Double t = reader.nextDouble();
                sen.setTime(LocalDateTime.ofEpochSecond(t.longValue(), 0, ZoneOffset.UTC));
                break;
            default:
                set = false;
                break;
        }
        return set;
    }

    protected abstract T asT(String val);

    // {
    // switch (genericType.getName()) {
    // case "String":
    // return (T) val;
    // case "Double":
    // return (T) Double.valueOf(val);
    // case "Integer":
    // return (T) Integer.valueOf(val);
    // default:
    // throw new IllegalArgumentException("Unsupported class type conversion: " + genericType.getName());
    // }
    // }
}
