package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;

import com.google.gson.stream.JsonReader;

public class RelayOutputTypeAdapter extends DeviceTypeAdapter<RelayOutput> {

    @Override
    protected RelayOutput create() {
        return new RelayOutput();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, RelayOutput dev) throws IOException {
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
