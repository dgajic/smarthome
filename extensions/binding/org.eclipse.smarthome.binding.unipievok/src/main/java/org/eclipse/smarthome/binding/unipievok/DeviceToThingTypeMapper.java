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
package org.eclipse.smarthome.binding.unipievok;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

public interface DeviceToThingTypeMapper {

    static ThingTypeUID mapToThingTypeUID(Device device) {
        if (device instanceof TemperatureSensor) {
            return THING_TYPE_TEMPERATURE_SENSOR;
        } else if (device instanceof Ds2438MultiSensor) {
            return THING_TYPE_DS2438_MULTI_SENSOR;
        } else if (device instanceof Digitalnput) {
            return THING_TYPE_DIGITAL_INPUT;
        } else if (device instanceof RelayOutput) {
            return THING_TYPE_RELAY_OUTPUT;
        } else {
            return null;
        }
    }
}
