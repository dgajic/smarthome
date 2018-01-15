package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import static org.junit.Assert.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NeuronTypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(Neuron.class, new NeuronTypeAdapter()).create();
    }

    @Test
    public void readTest() {
        // @formatter:off
        String json = "{" +
                "\"ver2\": \"1.0\"," +
                "\"dev\": \"neuron\"," +
                "\"glob_dev_id\": 1," +
                "\"sn\": 126," +
                "\"circuit\": \"1\"," +
                "\"model\": \"M203\"," +
                "\"board_count\": 2" +
                "}";
        // @formatter:on

        Neuron neuron = gson.fromJson(json, Neuron.class);
        assertNotNull(neuron);

        assertEquals("1", neuron.getId());
        assertEquals(1, neuron.getGlobDevId());
        assertEquals(Integer.valueOf(126), neuron.getSerialNumber());
        assertEquals("M203", neuron.getModel());
        assertEquals(Integer.valueOf(2), neuron.getBoardCount());
        assertEquals(2, neuron.getProperties().size());
        assertEquals("1.0", neuron.getProperty("ver2"));
        assertEquals("neuron", neuron.getProperty("dev"));

    }

    @Test
    public void readTestUart() {
        // @formatter:off
        String json = "{" +
                "\"uart_circuit\": \"1_01\"," +
                "\"version_registers\": [" +
                "1284," +
                "2062," +
                "0," +
                "1296," +
                "528," +
                "2747," +
                "0," +
                "4," +
                "5000," +
                "1521" +
                "]," +
                "\"dev\": \"neuron\"," +
                "\"circuit\": \"UART_15_2\"," +
                "\"model\": \"xS40\"," +
                "\"uart_port\": \"/dev/extcomm/0/0\"" +
                "}";
        // @formatter:on

        Neuron neuron = gson.fromJson(json, Neuron.class);
        assertNotNull(neuron);

        assertEquals("UART_15_2", neuron.getId());
        assertEquals("xS40", neuron.getModel());
        assertEquals(3, neuron.getProperties().size());

    }
}
