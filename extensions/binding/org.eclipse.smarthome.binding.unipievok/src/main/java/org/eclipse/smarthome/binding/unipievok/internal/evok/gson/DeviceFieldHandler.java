package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.util.function.BiConsumer;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

import com.google.gson.stream.JsonReader;

@FunctionalInterface
public interface DeviceFieldHandler<T extends Device> extends BiConsumer<T, JsonReader> {

    @Override
    default void accept(T dev, JsonReader reader) {
        try {
            acceptThrows(dev, reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T dev, JsonReader reader) throws Exception;
}
