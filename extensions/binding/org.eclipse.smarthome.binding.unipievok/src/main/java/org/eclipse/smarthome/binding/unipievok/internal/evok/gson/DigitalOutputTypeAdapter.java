package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutput;

public class DigitalOutputTypeAdapter extends DeviceTypeAdapter<DigitalOutput> {

    @Override
    protected DigitalOutput create() {
        return new DigitalOutput();
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
