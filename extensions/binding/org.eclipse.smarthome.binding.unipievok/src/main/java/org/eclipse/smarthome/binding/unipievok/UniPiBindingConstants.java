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
import org.eclipse.smarthome.core.thing.type.ChannelTypeUID;

/**
 * The {@link UniPiBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Dragan Gajic - Initial contribution
 */
@NonNullByDefault
public class UniPiBindingConstants {

    public static final String BINDING_ID = "unipievok";

    // bridge
    public static final ThingTypeUID THING_TYPE_BRIDGE = new ThingTypeUID(BINDING_ID, "bridge");

    // generic thing types
    public static final ThingTypeUID THING_TYPE_TEMPERATURE_SENSOR = new ThingTypeUID(BINDING_ID, "temperatureSensor");
    public static final ThingTypeUID THING_TYPE_DS2438_MULTI_SENSOR = new ThingTypeUID(BINDING_ID, "ds2438MultiSensor");
    public static final ThingTypeUID THING_TYPE_DIGITAL_INPUT = new ThingTypeUID(BINDING_ID, "digitalInput");
    public static final ThingTypeUID THING_TYPE_RELAY_OUTPUT = new ThingTypeUID(BINDING_ID, "relayOutput");

    // generic channel types
    public static final ChannelTypeUID TEMP_CHANNEL_TYPE_ID = new ChannelTypeUID(BINDING_ID, "temperature");
    public static final ChannelTypeUID HUMIDIY_CHANNEL_TYPE_ID = new ChannelTypeUID(BINDING_ID, "humidity");
    public static final ChannelTypeUID ILLUMINANCE_CHANNEL_TYPE_ID = new ChannelTypeUID(BINDING_ID, "illuminance");
    public static final ChannelTypeUID DINPUT_CHANNEL_TYPE_ID = new ChannelTypeUID(BINDING_ID, "dinput");
    public static final ChannelTypeUID RELAY_CHANNEL_TYPE_ID = new ChannelTypeUID(BINDING_ID, "relay");

    // bridge config properties
    public static final String API_URL = "evokApiUrl";
    public static final String SERIAL_NUMBER = "serialNumber";
    public static final String POLLING_INTERVAL = "pollingInterval";

    // sensor field name constants
    public static final String TEMPERATURE = "temperature";
    public static final String HUMIDITY = "humidity";
    public static final String ILLUMINANCE = "illuminance";

}
