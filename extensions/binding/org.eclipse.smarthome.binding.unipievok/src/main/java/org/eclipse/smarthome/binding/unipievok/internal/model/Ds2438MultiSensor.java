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

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Ds2438MultiSensor extends MultiSensor {

    private static final Set<String> FIELDS = new HashSet<>(Arrays.asList(TEMPERATURE, HUMIDITY, ILLUMINANCE));

    public Ds2438MultiSensor() {
        // @formatter:off
        MultiValue value = new MultiValue.Builder()
                .declare(TEMPERATURE, Double.class)
                .declare(HUMIDITY, Double.class)
                .declare(ILLUMINANCE, Double.class)
                .build();
        // @formatter:on
        this.setValue(value);
    }

    @Override
    public Set<String> supportedFields() {
        return FIELDS;
    }

    public Double getTemperature() {
        return getValue().get(TEMPERATURE, Double.class);
    }

    public Double getHumidity() {
        return getValue().get(HUMIDITY, Double.class);
    }

    public Double getIlluminance() {
        return getValue().get(ILLUMINANCE, Double.class);
    }
}
