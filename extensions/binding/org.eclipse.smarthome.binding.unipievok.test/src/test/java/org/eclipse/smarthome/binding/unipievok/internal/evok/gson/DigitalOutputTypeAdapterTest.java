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

import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutput;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DigitalOutputTypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(DigitalOutput.class, new DigitalOutputTypeAdapter())
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

        DigitalOutput dev = gson.fromJson(json, DigitalOutput.class);
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
