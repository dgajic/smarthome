package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutput;

import com.google.gson.stream.JsonReader;

public class DigitalOutputTypeAdapter extends DeviceTypeAdapter<DigitalOutput> {

    @Override
    protected DigitalOutput create() {
        return new DigitalOutput();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, DigitalOutput dev) throws IOException {
        boolean set = true;
        switch (name) {
            case "value":
                dev.set(reader.nextInt() != 0);
                break;
            case "pending":
                dev.setPending(reader.nextBoolean());
                break;
            default:
                set = false;
                break;
        }
        return set;
    }

}
