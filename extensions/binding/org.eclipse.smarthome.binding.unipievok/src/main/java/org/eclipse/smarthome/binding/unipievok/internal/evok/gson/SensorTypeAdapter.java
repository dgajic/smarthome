package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public class TemperatureSensorTypeAdapter extends DeviceTypeAdapter<TemperatureSensor> {

    @Override
    protected TemperatureSensor create() {
        return new TemperatureSensor();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, TemperatureSensor sen) throws IOException {
        boolean set = true;
        switch (name) {
            case "interval":
                sen.setInterval(reader.nextInt());
                break;
            case "value":
                sen.setValue(reader.nextDouble());
                break;
            case "circuit":
                sen.setId(reader.nextString());
                break;
            case "address":
                sen.setAddress(reader.nextString());
                break;
            case "time":
                Double t = reader.nextDouble();
                sen.setTime(LocalDateTime.ofEpochSecond(t.longValue(), 0, ZoneOffset.UTC));
                break;
            case "typ":
                sen.setTyp(reader.nextString());
                break;
            default:
                set = false;
                break;
        }
        return set;
    }

    @Override
    public void write(JsonWriter writer, TemperatureSensor sen) throws IOException {
        // TODO Auto-generated method stub
    }

}
