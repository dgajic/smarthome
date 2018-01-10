package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalnputDevice;

import com.google.gson.stream.JsonReader;

public class DigitalInputDeviceTypeAdapter extends BinaryDeviceTypeAdapter<DigitalnputDevice> {

    @Override
    protected DigitalnputDevice create() {
        return new DigitalnputDevice();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, DigitalnputDevice dev) throws IOException {
        boolean set = true;
        switch (name) {
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
