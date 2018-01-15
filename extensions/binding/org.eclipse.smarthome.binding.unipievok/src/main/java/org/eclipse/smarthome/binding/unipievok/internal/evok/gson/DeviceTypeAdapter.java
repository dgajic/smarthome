package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

public abstract class DeviceTypeAdapter<T extends Device> extends TypeAdapter<T> {

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
            switch (name) {
                case "glob_dev_id":
                    dev.setGlobDevId(reader.nextInt());
                    break;
                case "circuit":
                    dev.setId(reader.nextString());
                    break;
                default:
                    if (!handleAdditionalField(reader, name, dev)) {
                        // if additional field NOT handled then put it as property (only if number, string or boolean)
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
                    break;
            }
        }
        reader.endObject();
        return dev;
    }

    /**
     * Set additional fields of <T> via setters. If the field is set, it is expected reader is moved to next token and
     * method must return true. If the field is not set, reader must not be moved to next token.
     *
     * @param reader {@link JsonReader} on current position.
     * @param name Name of the json field.
     * @param dev
     * @return true if field has been set with value from json name
     * @throws IOException
     */
    protected abstract boolean handleAdditionalField(JsonReader reader, String name, T dev) throws IOException;

    @Override
    public void write(JsonWriter writer, T dev) throws IOException {
        // TODO Auto-generated method stub
    }
}
