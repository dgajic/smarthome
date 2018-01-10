package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import static org.junit.Assert.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalnputDevice;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DigitalInputDeviceTypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(DigitalnputDevice.class, new DigitalInputDeviceTypeAdapter())
                .create();
    }

    @Test
    public void readTest() {
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

        DigitalnputDevice dev = gson.fromJson(json, DigitalnputDevice.class);
        assertNotNull(dev);

        assertEquals("1_01", dev.getId());
        assertEquals(1, dev.getGlobDevId());
        assertEquals(false, dev.isSet());
        assertEquals(50, dev.getDebounce());
        assertEquals(Integer.valueOf(0), dev.getCounter());
        assertEquals(2, dev.getProperties().size());
        assertEquals("input", dev.getProperty("dev"));
        assertEquals("Simple", dev.getProperty("mode"));

    }
}
