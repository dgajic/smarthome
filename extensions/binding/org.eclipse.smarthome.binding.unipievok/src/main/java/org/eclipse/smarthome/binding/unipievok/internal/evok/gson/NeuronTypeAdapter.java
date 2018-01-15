package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;

import com.google.gson.stream.JsonReader;

public class NeuronTypeAdapter extends DeviceTypeAdapter<Neuron> {

    @Override
    protected Neuron create() {
        return new Neuron();
    }

    @Override
    protected boolean handleAdditionalField(JsonReader reader, String name, Neuron dev) throws IOException {
        boolean set = true;
        switch (name) {
            case "sn":
                dev.setSerialNumber(reader.nextInt());
                break;
            case "model":
                dev.setModel(reader.nextString());
                break;
            case "board_count":
                dev.setBoardCount(reader.nextInt());
                break;
            default:
                set = false;
                break;
        }
        return set;
    }
}
