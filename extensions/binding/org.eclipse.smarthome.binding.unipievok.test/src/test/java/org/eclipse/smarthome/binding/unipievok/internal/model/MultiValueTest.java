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
package org.eclipse.smarthome.binding.unipievok.internal.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MultiValueTest {

    private MultiValue val;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        // @formatter:off
        val = new MultiValue.Builder()
                .declare("temp", Double.class)
                .declare("count", Integer.class)
                .declare("state", Boolean.class)
                .build();
        // @formatter:on
    }

    @Test
    public void testSetGet() {
        val.set("temp", 2.3);
        val.set("count", 42);
        val.set("state", false);

        assertEquals(Double.valueOf(2.3), val.get("temp", Double.class));
        assertEquals(Integer.valueOf(42), val.get("count", Integer.class));
        assertEquals(Boolean.valueOf(false), val.get("state", Boolean.class));
    }

    @Test
    public void testSetUnknownField() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unknown field temp1");

        val.set("temp1", 2.3);
    }

    @Test
    public void testSetWrongType() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field count is defined as Integer but passed value is Double");

        val.set("count", 2.3);
    }

    @Test
    public void testGetUnknownField() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Unknown field temp1");

        val.get("temp1", Double.class);
    }

    @Test
    public void testGetWrongType() throws IllegalArgumentException {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Field count is defined as Integer but passed value is Double");

        val.get("count", Double.class);
    }
}
