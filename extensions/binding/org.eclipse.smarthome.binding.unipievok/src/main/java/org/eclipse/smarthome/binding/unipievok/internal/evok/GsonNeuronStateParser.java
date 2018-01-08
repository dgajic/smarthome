package org.eclipse.smarthome.binding.unipievok.internal.evok;

import java.lang.reflect.Type;

import org.eclipse.smarthome.binding.unipievok.internal.UniPiServiceException;
import org.eclipse.smarthome.binding.unipievok.internal.model.BinaryDevice;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;

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
        gson = new GsonBuilder().registerTypeAdapter(Neuron.class, new NeuronDeserializer())
                .registerTypeAdapter(BinaryDevice.class, new BinaryDeviceDeserializer()).create();
    }

    @Override
    public Neuron parse(String json) {
        JsonParser parser = new JsonParser();

        try {
            JsonElement element = parser.parse(json);
            JsonObject root = element.getAsJsonObject();
            JsonArray data = root.getAsJsonArray("data");
            Neuron neuron = new Neuron();

            data.forEach(e -> {
                JsonObject device = e.getAsJsonObject();

                switch (device.get("dev").getAsString()) {
                    case "neuron":
                        neuron.simpleCopy(gson.fromJson(device, Neuron.class));
                        break;
                    case "input":
                        neuron.getDigitalInputs().addDevice(gson.fromJson(device, BinaryDevice.class));
                        break;
                    case "relay":
                        BinaryDevice bdev = gson.fromJson(device, BinaryDevice.class);
                        if ("physical".equals(device.get("relay_type").getAsString())) {
                            neuron.getRelayOutputs().addDevice(bdev);
                        } else if ("digital".equals(device.get("relay_type").getAsString())) {
                            neuron.getDigitalOutputs().addDevice(bdev);
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
            neuron.setId(obj.get("sn").getAsString());
            neuron.setModel(obj.get("model").getAsString());
            neuron.setSerialNumber(obj.get("sn").getAsString());
            neuron.setCircuit(obj.get("circuit").getAsString());
            neuron.setBoardCount(obj.get("board_count").getAsInt());

            return neuron;
        }

    }

    static class BinaryDeviceDeserializer implements JsonDeserializer<BinaryDevice> {

        @Override
        public BinaryDevice deserialize(JsonElement el, Type type, JsonDeserializationContext ctx)
                throws JsonParseException {
            JsonObject obj = el.getAsJsonObject();

            BinaryDevice bdev = new BinaryDevice();
            bdev.setId(obj.get("circuit").getAsString());
            bdev.set(obj.get("value").getAsInt() != 0);
            bdev.setProperty("mode", obj.get("mode").getAsString());
            bdev.setProperty("glob_dev_id", obj.get("glob_dev_id").getAsString());

            switch (obj.get("dev").getAsString()) {
                case "input":
                    bdev.setProperty("debounce", obj.get("debounce").getAsInt());
                    bdev.setProperty("counter", obj.get("counter").getAsInt());
                    bdev.setProperty("counter_mode", obj.get("counter_mode").getAsString());
                    break;
                case "relay":
                    bdev.setProperty("pending", obj.get("pending").getAsBoolean());

            }

            return bdev;
        }

    }

}
