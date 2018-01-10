package org.eclipse.smarthome.binding.unipievok.internal.evok;

import static org.junit.Assert.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.BinaryDevice;
import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalnputDevice;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.junit.Test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonNeuronStateParserTest {

    @Test
    public void neuronDeserializerTest() {
        JsonObject obj = new JsonObject();

        obj.addProperty("sn", 123);
        obj.addProperty("model", "M203");
        obj.addProperty("circuit", "1");
        obj.addProperty("glob_dev_id", 1);

        Neuron neuron = new GsonNeuronStateParser.NeuronDeserializer().deserialize(obj, null, null);
        assertNotNull(neuron);

        assertEquals("1", neuron.getId());
        assertEquals(Integer.valueOf(123), neuron.getSerialNumber());
        assertEquals("M203", neuron.getModel());
        assertEquals("1", neuron.getCircuit());
        assertEquals(1, neuron.getGlobDevId());
        assertNull(neuron.getBoardCount());

    }

    @Test
    public <DigitalInput> void binaryDeviceDeserializerTestInput() {
        // @formatter:off
        String json = "{" +
                    "\"glob_dev_id\": 1," +
                    "\"modes\": [" +
                    "\"Simple\"," +
                    "\"DirectSwitch\"" +
                    "]," +
                    "\"value\": 0," +
                    "\"circuit\": \"1_01\"," +
                    "\"debounce\": 50," +
                    "\"counter\": 0," +
                    "\"counter_mode\": \"Enabled\"," +
                    "\"dev\": \"input\"," +
                    "\"mode\": \"Simple\"" +
                    "}";
        // @formatter:on

        JsonElement el = new JsonParser().parse(json);

        BinaryDevice bdev = new GsonNeuronStateParser.BinaryDeviceDeserializer<DigitalnputDevice>().deserialize(el,
                DigitalnputDevice.class, null);
        assertNotNull(bdev);

    }

}
