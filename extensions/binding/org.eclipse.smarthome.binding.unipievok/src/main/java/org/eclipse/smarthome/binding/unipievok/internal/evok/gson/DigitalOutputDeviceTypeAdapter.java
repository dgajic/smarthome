package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutputDevice;

import com.google.gson.stream.JsonReader;

public class DigitalOutputDeviceTypeAdapter extends BinaryDeviceTypeAdapter<DigitalOutputDevice> {

    @Override
    protected DigitalOutputDevice create() {
        return new DigitalOutputDevice();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, DigitalOutputDevice dev)
            throws IOException {
        boolean set = true;
        switch (name) {
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
