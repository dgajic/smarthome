package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import static org.junit.Assert.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutputDevice;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RealyOutputDeviceTypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(RelayOutputDevice.class, new RelayOutputDeviceTypeAdapter())
                .create();
    }

    @Test
    public void readTest() {
        // @formatter:off
        String json = "{" +
                "\"glob_dev_id\": 1," +
                "\"modes\": [" +
                "\"Simple\"" +
                "]," +
                "\"value\": 0," +
                "\"circuit\": \"2_01\"," +
                "\"pending\": false," +
                "\"relay_type\": \"physical\"," +
                "\"dev\": \"relay\"," +
                "\"mode\": \"Simple\"" +
                "}";
        // @formatter:on

        RelayOutputDevice dev = gson.fromJson(json, RelayOutputDevice.class);
        assertNotNull(dev);

        assertEquals("2_01", dev.getId());
        assertEquals(1, dev.getGlobDevId());
        assertEquals(false, dev.isSet());
        assertEquals(false, dev.isPending());
        assertEquals(3, dev.getProperties().size());
        assertEquals("relay", dev.getProperty("dev"));
        assertEquals("Simple", dev.getProperty("mode"));
        assertEquals("physical", dev.getProperty("relay_type"));

    }
}
