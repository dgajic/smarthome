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

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;

/**
 *
 * @author Dragan Gajic
 *
 * @param <S>
 */
public abstract class SensorTypeAdapter<S extends Sensor<?>> extends DeviceTypeAdapter<S> {

    @Override
    protected abstract S create();

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("time", (dev, reader) -> {
            Double t = reader.nextDouble();
            dev.setTime(LocalDateTime.ofEpochSecond(t.longValue(), 0, ZoneOffset.UTC));
        });
    }
}
