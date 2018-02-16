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

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import java.util.Optional;

import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;

/**
 *
 * @author Dragan Gajic
 *
 */
public class Ds2438TypeAdapter extends SensorTypeAdapter<Ds2438MultiSensor> {

    @Override
    protected Ds2438MultiSensor create() {
        return new Ds2438MultiSensor();
    }

    @Override
    protected void registerAdditionalHandlers() {
        super.registerAdditionalHandlers();

        registerHandler("temp", (dev, reader) -> {
            dev.getValue().set(TEMPERATURE, Optional.ofNullable(reader.nextDouble()).orElse(null));
        });

        registerHandler(HUMIDITY, (dev, reader) -> {
            dev.getValue().set(HUMIDITY, Optional.ofNullable(reader.nextDouble()).orElse(null));
        });

        registerHandler(ILLUMINANCE, (dev, reader) -> {
            dev.getValue().set(ILLUMINANCE, Optional.ofNullable(reader.nextDouble()).orElse(null));
        });
    }

}
