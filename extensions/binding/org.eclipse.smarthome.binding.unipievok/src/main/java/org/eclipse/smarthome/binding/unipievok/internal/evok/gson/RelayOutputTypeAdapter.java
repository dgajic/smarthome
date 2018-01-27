package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;

public class RelayOutputTypeAdapter extends DeviceTypeAdapter<RelayOutput> {

    @Override
    protected RelayOutput create() {
        return new RelayOutput();
    }

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("value", (dev, reader) -> {
            dev.set(reader.nextInt() != 0);
        });

        registerHandler("pending", (dev, reader) -> {
            dev.setPending(reader.nextBoolean());
        });

    }
}
