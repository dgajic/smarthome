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

import java.util.Optional;

import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;

public class Ds18b20TypeAdapter extends SensorTypeAdapter<TemperatureSensor> {

    @Override
    protected TemperatureSensor create() {
        return new TemperatureSensor();
    }

    @Override
    protected void registerAdditionalHandlers() {
        super.registerAdditionalHandlers();

        registerHandler("value", (dev, reader) -> {
            dev.setValue(Optional.ofNullable(reader.nextDouble()).orElse(null));
        });
    }
}
