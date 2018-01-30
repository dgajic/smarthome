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
package org.eclipse.smarthome.binding.unipievok.internal;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.binding.unipievok.handler.UniPiBridgeHandler;
import org.eclipse.smarthome.binding.unipievok.handler.UniPiDigitalInputsHandler;
import org.eclipse.smarthome.binding.unipievok.handler.UniPiSensorsHandler;
import org.eclipse.smarthome.binding.unipievok.internal.discovery.UniPiDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryService;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link UniPiThingHandlerFactory} is responsible for creating things and thing
 * handlers. This factory also creates discovery service for UniPiPLC bridge which will
 * be used to register all available devices.
 *
 * @author Dragan Gajic - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, immediate = true, configurationPid = "binding.unipievok")
@NonNullByDefault
public class UniPiThingHandlerFactory extends BaseThingHandlerFactory {

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections
            .unmodifiableSet(new HashSet<>(Arrays.asList(THING_TYPE_BRIDGE, THING_TYPE_TEMPERATURE_SENSOR,
                    THING_TYPE_DS2438_MULTI_SENSOR, THING_TYPE_DIGITAL_INPUT)));

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (THING_TYPE_BRIDGE.equals(thingTypeUID)) {

            UniPiBridgeHandler bridgeHandler = new UniPiBridgeHandler((Bridge) thing);
            bundleContext.registerService(DiscoveryService.class.getName(),
                    new UniPiDiscoveryService(bridgeHandler, 10), new Hashtable<String, Object>());
            return bridgeHandler;

        } else if (THING_TYPE_TEMPERATURE_SENSOR.equals(thingTypeUID)
                || THING_TYPE_DS2438_MULTI_SENSOR.equals(thingTypeUID)) {

            return new UniPiSensorsHandler(thing);

        } else if (THING_TYPE_DIGITAL_INPUT.equals(thingTypeUID)) {

            return new UniPiDigitalInputsHandler(thing);
        }
        return null;
    }
}
