package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class DeviceTypeAdapter<T extends Device> extends TypeAdapter<T> {

    private final Map<String, DeviceFieldHandler<T>> handlers = new HashMap<>();

    public DeviceTypeAdapter() {

        registerHandler("glob_dev_id", (dev, reader) -> {
            dev.setGlobDevId(reader.nextInt());
        });

        registerHandler("circuit", (dev, reader) -> {
            dev.setId(reader.nextString());
        });

        registerAdditionalHandlers();
    }

    /**
     * Use this method to register {@link DeviceTypeAdapter} specific field handlers.
     */
    protected void registerAdditionalHandlers() {
    }

    /**
     *
     * @return
     */
    protected abstract T create();

    @Override
    public T read(JsonReader reader) throws IOException {

        T dev = create();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            DeviceFieldHandler<T> handler = handlers.get(name);

            if (handler != null) {
                handler.accept(dev, reader);
            } else {
                // if field NOT handled then put it as property (only if number, string or boolean)
                switch (reader.peek()) {
                    case BOOLEAN:
                        dev.setProperty(name, reader.nextBoolean());
                        break;
                    case STRING:
                        dev.setProperty(name, reader.nextString());
                        break;
                    case NUMBER:
                        dev.setProperty(name, reader.nextDouble());
                        break;
                    default:
                        reader.skipValue();
                        break;
                }
            }
        }
        reader.endObject();
        return dev;
    }

    protected void registerHandler(String name, DeviceFieldHandler<T> handler) {
        handlers.put(name, handler);
    }

    @Override
    public void write(JsonWriter writer, T dev) throws IOException {
        // TODO Auto-generated method stub
    }
}
