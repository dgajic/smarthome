package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;

public class DigitalInputTypeAdapter extends DeviceTypeAdapter<Digitalnput> {

    @Override
    protected Digitalnput create() {
        return new Digitalnput();
    }

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("value", (dev, reader) -> {
            dev.set(reader.nextInt() != 0);
        });

        registerHandler("debounce", (dev, reader) -> {
            dev.setDebounce(reader.nextInt());
        });

        registerHandler("counter", (dev, reader) -> {
            dev.setCounter(reader.nextInt());
        });

        registerHandler("counter_mode", (dev, reader) -> {
            dev.setCounterMode(reader.nextString());
        });
    }

}
