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

import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DigitalInputTypeAdapterTest {
    private Gson gson;

    @Before
    public void setUp() {
        gson = new GsonBuilder().registerTypeAdapter(Digitalnput.class, new DigitalInputTypeAdapter())
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

        Digitalnput dev = gson.fromJson(json, Digitalnput.class);
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
