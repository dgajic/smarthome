/**
 * Copyright (c) 2014,2017 Contributors to the Eclipse Foundation
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

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link UniPiEvokBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Dragan Gajic - Initial contribution
 */
@NonNullByDefault
public class UniPiEvokBindingConstants {

    private static final String BINDING_ID = "unipievok";

    // bridge
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");

    // generic thing types
    public static final ThingTypeUID THING_TYPE_RELAY = new ThingTypeUID(BINDING_ID, "relay");
    public static final ThingTypeUID THING_TYPE_TEMPERATURE_SENSOR = new ThingTypeUID(BINDING_ID, "temperature");
    public static final ThingTypeUID THING_TYPE_DIGITAL_INPUT = new ThingTypeUID(BINDING_ID, "digitalInput");

    // List of all Channel ids
    public static final String CHANNEL_RELAY = "relay";
    public static final String CHANNEL_TEMPERATURE = "temp";
    public static final String CHANNEL_DIGITAL_INPUT = "dinput";

    // Bridge config properties
    public static final String API_URL = "evokApiUrl";
    public static final String SERIAL_NUMBER = "serialNumber";
    public static final String POLLING_INTERVAL = "pollingInterval";

    // Light config properties
    public static final String CIRCUIT = "circuit";
    public static final String TYPE = "type";

}
