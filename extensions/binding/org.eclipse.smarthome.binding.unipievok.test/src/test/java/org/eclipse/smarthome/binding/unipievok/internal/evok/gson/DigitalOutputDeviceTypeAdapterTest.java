package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import static org.junit.Assert.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutputDevice;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DigitalOutputDeviceTypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(DigitalOutputDevice.class, new DigitalOutputDeviceTypeAdapter())
                .create();
    }

    @Test
    public void readTest() {
        // @formatter:off
        String json = "{" +
                "\"glob_dev_id\": 1," +
                "\"modes\": [" +
                "\"Simple\"," +
                "\"PWM\"" +
                "]," +
                "\"value\": 0," +
                "\"circuit\": \"1_01\"," +
                "\"alias\": \"al_lights_kitchen\"," +
                "\"pending\": true," +
                "\"relay_type\": \"digital\"," +
                "\"dev\": \"relay\"," +
                "\"mode\": \"Simple\"" +
                "}";
        // @formatter:on

        DigitalOutputDevice dev = gson.fromJson(json, DigitalOutputDevice.class);
        assertNotNull(dev);

        assertEquals("1_01", dev.getId());
        assertEquals(1, dev.getGlobDevId());
        assertEquals(false, dev.isSet());
        assertEquals(true, dev.isPending());
        assertEquals(4, dev.getProperties().size());
        assertEquals("relay", dev.getProperty("dev"));
        assertEquals("Simple", dev.getProperty("mode"));
        assertEquals("al_lights_kitchen", dev.getProperty("alias"));
        assertEquals("digital", dev.getProperty("relay_type"));

    }
}
