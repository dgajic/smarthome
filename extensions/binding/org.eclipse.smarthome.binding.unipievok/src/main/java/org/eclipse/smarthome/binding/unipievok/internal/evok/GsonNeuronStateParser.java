package org.eclipse.smarthome.binding.unipievok.internal.evok;

import java.lang.reflect.Type;
import java.util.Optional;

import org.eclipse.smarthome.binding.unipievok.internal.UniPiServiceException;
import org.eclipse.smarthome.binding.unipievok.internal.model.BinaryDevice;
import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutputDevice;
import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalnputDevice;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutputDevice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class GsonNeuronStateParser implements NeuronStateParser {
    private final Gson gson;

    public GsonNeuronStateParser() {
        // @formatter:off
        gson = new GsonBuilder()
                .registerTypeAdapter(Neuron.class, new NeuronDeserializer())
                .registerTypeAdapter(DigitalnputDevice.class, new BinaryDeviceDeserializer<DigitalnputDevice>())
                .registerTypeAdapter(DigitalOutputDevice.class, new BinaryDeviceDeserializer<DigitalOutputDevice>())
                .registerTypeAdapter(RelayOutputDevice.class, new BinaryDeviceDeserializer<RelayOutputDevice>())
                .create();
        // @formatter:on
    }

    @Override
    public Neuron parse(String json) {
        JsonParser parser = new JsonParser();

        try {
            JsonElement element = parser.parse(json);
            JsonObject root = element.getAsJsonObject();
            JsonArray data = root.getAsJsonArray("data");

            // validate

            Neuron neuron = new Neuron();

            data.forEach(e -> {
                JsonObject device = e.getAsJsonObject();

                switch (device.get("dev").getAsString()) {
                    case "neuron":
                        neuron.simpleCopy(gson.fromJson(device, Neuron.class));
                        break;
                    case "input":
                        neuron.getDigitalInputs().addDevice(gson.fromJson(device, DigitalnputDevice.class));
                        break;
                    case "relay":
                        String type = device.get("relay_type").getAsString();
                        if ("physical".equals(type)) {
                            neuron.getRelayOutputs().addDevice(gson.fromJson(device, RelayOutputDevice.class));
                        } else if ("digital".equals(type)) {
                            neuron.getDigitalOutputs().addDevice(gson.fromJson(device, DigitalOutputDevice.class));
                        }
                        break;
                }
            });

            return neuron;

        } catch (JsonSyntaxException jse) {
            throw new UniPiServiceException("neuron.parser.error", jse);
        } catch (IllegalStateException ise) {
            throw new UniPiServiceException("neuron.parser.syntax.error", ise);
        }
    }

    static class NeuronDeserializer implements JsonDeserializer<Neuron> {

        @Override
        public Neuron deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            JsonObject obj = el.getAsJsonObject();

            Neuron neuron = new Neuron();
            neuron.setId(obj.get("circuit").getAsString());
            neuron.setCircuit(obj.get("circuit").getAsString());
            neuron.setGlobDevId(obj.get("glob_dev_id").getAsInt());

            Optional.ofNullable(obj.get("model")).ifPresent(v -> neuron.setModel(v.getAsString()));
            Optional.ofNullable(obj.get("sn")).ifPresent(v -> neuron.setSerialNumber(v.getAsInt()));
            Optional.ofNullable(obj.get("board_count")).ifPresent(v -> neuron.setBoardCount(v.getAsInt()));

            return neuron;
        }

    }

    static class BinaryDeviceDeserializer<T extends BinaryDevice> implements JsonDeserializer<T> {

        @Override
        public T deserialize(JsonElement el, Type type, JsonDeserializationContext ctx) throws JsonParseException {
            try {
                @SuppressWarnings("unchecked")
                T bdev = (T) type.getClass().newInstance();
                JsonObject obj = el.getAsJsonObject();

                bdev.setId(obj.get("circuit").getAsString());
                bdev.set(obj.get("value").getAsInt() != 0);
                bdev.setGlobDevId(obj.get("glob_dev_id").getAsInt());
                Optional.ofNullable(obj.get("mode")).ifPresent(v -> bdev.setProperty("mode", v.getAsString()));

                switch (type.getTypeName()) {
                    case "input":
                        bdev.setProperty("debounce", obj.get("debounce").getAsInt());
                        bdev.setProperty("counter_mode", obj.get("counter_mode").getAsString());
                        Optional.ofNullable(obj.get("counter"))
                                .ifPresent(v -> bdev.setProperty("counter", v.getAsString()));
                        break;
                    case "relay":
                        bdev.setProperty("pending", obj.get("pending").getAsBoolean());
                        break;
                }
                return bdev;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new JsonParseException(e);
            }
        }
    }
}
