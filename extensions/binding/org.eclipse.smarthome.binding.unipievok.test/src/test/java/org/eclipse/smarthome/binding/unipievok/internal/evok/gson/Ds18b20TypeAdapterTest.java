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

import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ds18b20TypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(TemperatureSensor.class, new Ds18b20TypeAdapter())
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
        assertEquals(Double.valueOf(8.8), sen.getValue());
        assertEquals("289679B809000080", sen.getProperty("address"));
        assertEquals(15.0, sen.getProperty("interval"));
        assertEquals("DS18B20", sen.getProperty("typ"));
        assertEquals("temp", sen.getProperty("dev"));

    }
}
