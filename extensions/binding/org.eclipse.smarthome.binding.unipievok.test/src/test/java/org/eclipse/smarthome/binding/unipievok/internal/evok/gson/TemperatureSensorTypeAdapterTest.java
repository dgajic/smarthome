package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import static org.junit.Assert.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class TemperatureSensorTypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(TemperatureSensor.class, new TemperatureSensorTypeAdapter())
                .create();
    }

    @Test
    public void readTest() {
        // @formatter:off
        String json = "{" +
                "\"interval\": 15," +
                "\"value\": 8.8," +
                "\"circuit\": \"289679B809000080\"," +
                "\"address\": \"289679B809000080\"," +
                "\"time\": 1515627757.309789," +
                "\"typ\": \"DS18B20\"," +
                "\"lost\": false," +
                "\"dev\": \"temp\"" +
                "}";
        // @formatter:on

        TemperatureSensor sen = gson.fromJson(json, TemperatureSensor.class);
        assertNotNull(sen);

        assertEquals("289679B809000080", sen.getId());
        assertEquals(Integer.valueOf(15), sen.getInterval());
        assertEquals(8.8, sen.getValue());
        assertEquals("289679B809000080", sen.getAddress());
        assertEquals("DS18B20", sen.getTyp());

    }
}
