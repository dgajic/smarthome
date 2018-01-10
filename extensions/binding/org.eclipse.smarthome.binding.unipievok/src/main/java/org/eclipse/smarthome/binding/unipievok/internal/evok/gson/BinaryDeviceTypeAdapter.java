package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.io.IOException;

import org.eclipse.smarthome.binding.unipievok.internal.model.BinaryDevice;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public abstract class BinaryDeviceTypeAdapter<T extends BinaryDevice> extends TypeAdapter<T> {

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
                case "value":
                    dev.set(reader.nextInt() != 0);
                    break;
                case "circuit":
                    dev.setId(reader.nextString());
                    break;
                default:
                    if (!handleAdditionalField(reader, name, dev)) {
                        // if additional field NOT handled then put it as property (only if number, string or boolean)
                        JsonToken token = reader.peek();
                        if (token.equals(JsonToken.NUMBER) || token.equals(JsonToken.STRING)
                                || token.equals(JsonToken.BOOLEAN)) {
                            dev.setProperty(name, reader.nextString());
                        } else {
                            // if not value property, then skip
                            reader.skipValue();
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
