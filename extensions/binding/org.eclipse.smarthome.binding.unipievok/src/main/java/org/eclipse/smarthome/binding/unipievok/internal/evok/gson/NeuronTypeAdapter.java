package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;

public class NeuronTypeAdapter extends DeviceTypeAdapter<Neuron> {

    @Override
    protected Neuron create() {
        return new Neuron();
    }

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("sn", (dev, reader) -> {
            dev.setSerialNumber(reader.nextInt());
        });

        registerHandler("model", (dev, reader) -> {
            dev.setModel(reader.nextString());
        });

        registerHandler("board_count", (dev, reader) -> {
            dev.setBoardCount(reader.nextInt());
        });

    }

}
