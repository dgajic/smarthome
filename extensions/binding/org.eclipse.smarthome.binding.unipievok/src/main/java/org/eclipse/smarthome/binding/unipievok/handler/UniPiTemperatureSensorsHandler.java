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
package org.eclipse.smarthome.binding.unipievok.handler;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.TEMPERATURE;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;

@NonNullByDefault
public class UniPiTemperatureSensorsHandler extends UniPiAbstractHandler<TemperatureSensor> {

    public UniPiTemperatureSensorsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public synchronized void onUpdate(TemperatureSensor temp) {
        if (temp.getValue() != null) {
            updateState(new ChannelUID(getThing().getUID(), TEMPERATURE), new DecimalType(temp.getValue()));
            logger.debug("Temperature updated for {}, with value {}", temp.getId(), temp.getValue());
        } else {
            logger.warn("Temperature for sensor {} is null", temp.getId());
        }
    }
}
