package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class CompleteStateDeserializer implements JsonDeserializer<Collection<Device>> {

    @Override
    public Collection<Device> deserialize(JsonElement element, Type type, JsonDeserializationContext ctx)
            throws JsonParseException {

        Collection<Device> res = new ArrayList<>();

        element.getAsJsonArray().forEach(e -> {
            JsonObject device = e.getAsJsonObject();
            switch (device.get("dev").getAsString()) {
                case "neuron":
                    res.add(ctx.deserialize(device, Neuron.class));
                    break;
                case "input":
                    res.add(ctx.deserialize(device, Digitalnput.class));
                    break;
                case "relay":
                    String relayType = device.get("relay_type").getAsString();
                    if ("physical".equals(relayType)) {
                        res.add(ctx.deserialize(device, RelayOutput.class));
                    } else if ("digital".equals(relayType)) {
                        res.add(ctx.deserialize(device, DigitalOutput.class));
                    }
                    break;
                case "temp":
                    res.add(ctx.deserialize(device, TemperatureSensor.class));
                    break;
            }
        });

        return res;
    }

}
