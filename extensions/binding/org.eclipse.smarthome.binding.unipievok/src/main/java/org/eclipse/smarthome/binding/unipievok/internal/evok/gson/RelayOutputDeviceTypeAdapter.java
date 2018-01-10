package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutputDevice;

import com.google.gson.stream.JsonReader;

public class RelayOutputDeviceTypeAdapter extends BinaryDeviceTypeAdapter<RelayOutputDevice> {

    @Override
    protected RelayOutputDevice create() {
        return new RelayOutputDevice();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, RelayOutputDevice dev) throws IOException {
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
