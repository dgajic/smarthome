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

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;

/**
 *
 * @author Dragan Gajic
 *
 */
@NonNullByDefault
public class UniPiDs2438MultiSensorsHandler extends UniPiAbstractHandler<Ds2438MultiSensor> {

    public UniPiDs2438MultiSensorsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public synchronized void onUpdate(Ds2438MultiSensor sen) {
        if (sen.getTemperature() != null) {
            updateState(new ChannelUID(getThing().getUID(), TEMPERATURE), new DecimalType(sen.getTemperature()));
            logger.debug("Temperature updated for {}, with value {}", sen.getId(), sen.getTemperature());
        }

        if (sen.getHumidity() != null) {
            updateState(new ChannelUID(getThing().getUID(), HUMIDITY), new DecimalType(sen.getHumidity()));
            logger.debug("Humidity updated for {}, with value {}", sen.getId(), sen.getHumidity());
        }

        if (sen.getIlluminance() != null) {
            updateState(new ChannelUID(getThing().getUID(), ILLUMINANCE), new DecimalType(sen.getIlluminance()));
            logger.debug("Illuminance updated for {}, with value {}", sen.getId(), sen.getIlluminance());
        }
    }
}
