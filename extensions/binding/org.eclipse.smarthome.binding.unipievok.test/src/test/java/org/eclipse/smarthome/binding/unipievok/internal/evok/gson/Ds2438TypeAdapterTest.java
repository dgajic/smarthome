/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import static org.junit.Assert.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ds2438TypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(Ds2438MultiSensor.class, new Ds2438TypeAdapter()).create();
    }

    @Test
    public void readTest() {
        // @formatter:off
        String json = "{" +
                "\"address\": \"265ACC14020000FA\"," +
                "\"typ\": \"DS2438\"," +
                "\"lost\": false," +
                "\"temp\": \"22.125\"," +
                "\"interval\": 15," +
                "\"vad\": \"2.03\"," +
                "\"humidity\": 63.024," +
                "\"illuminance\": \"395.442\"," +
                "\"vdd\": \"5.32\"," +
                "\"circuit\": \"265ACC14020000FA\"," +
                "\"time\": 1517006417.31678," +
                "\"dev\": \"temp\"" +
                "}";
        // @formatter:on

        Ds2438MultiSensor sen = gson.fromJson(json, Ds2438MultiSensor.class);
        assertNotNull(sen);

        assertEquals("265ACC14020000FA", sen.getId());
        assertEquals("265ACC14020000FA", sen.getProperty("address"));
        assertEquals(15.0, sen.getProperty("interval"));
        assertEquals("DS2438", sen.getProperty("typ"));
        assertEquals(false, sen.getProperty("lost"));
        assertEquals("2.03", sen.getProperty("vad"));
        assertEquals("5.32", sen.getProperty("vdd"));

        assertEquals(Double.valueOf(22.125), sen.getTemperature());
        assertEquals(Double.valueOf(63.024), sen.getHumidity());
        assertEquals(Double.valueOf(395.442), sen.getIlluminance());

    }
}
