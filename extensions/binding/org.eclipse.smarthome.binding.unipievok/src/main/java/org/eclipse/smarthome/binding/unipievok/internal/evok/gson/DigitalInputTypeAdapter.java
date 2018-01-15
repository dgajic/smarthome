package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;

import com.google.gson.stream.JsonReader;

public class DigitalInputTypeAdapter extends DeviceTypeAdapter<Digitalnput> {

    @Override
    protected Digitalnput create() {
        return new Digitalnput();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, Digitalnput dev) throws IOException {
        boolean set = true;
        switch (name) {
            case "value":
                dev.set(reader.nextInt() != 0);
                break;
            case "debounce":
                dev.setDebounce(reader.nextInt());
                break;
            case "counter":
                dev.setCounter(reader.nextInt());
                break;
            case "counter_mode":
                dev.setCounterMode(reader.nextString());
                break;
            default:
                set = false;
                break;
        }
        return set;
    }

}
