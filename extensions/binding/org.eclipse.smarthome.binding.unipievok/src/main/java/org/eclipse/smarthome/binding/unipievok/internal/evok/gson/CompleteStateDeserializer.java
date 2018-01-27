package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import java.lang.reflect.Type;
import java.util.stream.StreamSupport;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class CompleteStateDeserializer implements JsonDeserializer<Device[]> {

    @Override
    public Device[] deserialize(JsonElement element, Type type, JsonDeserializationContext ctx)
            throws JsonParseException {

        return StreamSupport.stream(element.getAsJsonArray().spliterator(), false).map(s -> {
            Device dev = null;
            JsonObject obj = s.getAsJsonObject();
            switch (safeGet(obj.get("dev"))) {
                case "neuron":
                    dev = ctx.deserialize(obj, Neuron.class);
                    break;
                case "input":
                    dev = ctx.deserialize(obj, Digitalnput.class);
                    break;
                case "relay":
                    String relayType = safeGet(obj.get("relay_type"));
                    if ("physical".equals(relayType)) {
                        dev = ctx.deserialize(obj, RelayOutput.class);
                    } else if ("digital".equals(relayType)) {
                        dev = ctx.deserialize(obj, DigitalOutput.class);
                    } else { // default is physical
                        dev = ctx.deserialize(obj, RelayOutput.class);
                    }
                    break;
                case "temp":
                    String typ = safeGet(obj.get("typ"));
                    if ("DS18B20".equals(typ)) {
                        dev = ctx.deserialize(obj, TemperatureSensor.class);
                    } else if ("DS2438".equals(typ)) {
                        dev = ctx.deserialize(obj, Ds2438MultiSensor.class);
                    }
                    break;
            }
            return dev;
        }).filter(dev -> dev != null).toArray(size -> new Device[size]);
    }

    private String safeGet(JsonElement el) {
        return el == null ? null : el.getAsString();
    }
}
