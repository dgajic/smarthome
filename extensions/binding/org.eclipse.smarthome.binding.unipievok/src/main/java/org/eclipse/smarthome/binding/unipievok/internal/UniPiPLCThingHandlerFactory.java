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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.binding.unipievok.UniPiEvokBindingConstants;
import org.eclipse.smarthome.binding.unipievok.handler.UniPiPLCBridgeHandler;
import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandlerFactory;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandlerFactory;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Component;

/**
 * The {@link UniPiPLCThingHandlerFactory} is responsible for creating things and thing
 * handlers. This factory also creates discovery service for UniPiPLC bridge which will
 * be used to register all available devices.
 *
 * @author Dragan Gajic - Initial contribution
 */
@Component(service = ThingHandlerFactory.class, immediate = true, configurationPid = "binding.unipievok")
@NonNullByDefault
public class UniPiPLCThingHandlerFactory extends BaseThingHandlerFactory {

    // Holds discovery services registered for handler so we can remove them when handler is removed
    private final Map<ThingUID, ServiceRegistration<?>> discoveryServiceRegistry = new HashMap<>();

    private static final Set<ThingTypeUID> SUPPORTED_THING_TYPES_UIDS = Collections
            .singleton(UniPiEvokBindingConstants.THING_TYPE_BRIDGE);

    @Override
    public boolean supportsThingType(ThingTypeUID thingTypeUID) {
        return SUPPORTED_THING_TYPES_UIDS.contains(thingTypeUID);
    }

    @Override
    public @Nullable Thing createThing(@NonNull ThingTypeUID thingTypeUID, @NonNull Configuration configuration,
            @Nullable ThingUID thingUID, @Nullable ThingUID bridgeUID) {
        // TODO Auto-generated method stub
        return super.createThing(thingTypeUID, configuration, thingUID, null);
    }

    @Override
    protected @Nullable ThingHandler createHandler(Thing thing) {
        ThingTypeUID thingTypeUID = thing.getThingTypeUID();

        if (thingTypeUID.equals(UniPiEvokBindingConstants.THING_TYPE_BRIDGE)) {
            registerDiscoveryService();
            return new UniPiPLCBridgeHandler((Bridge) thing);
        }

        return null;
    }

    private void registerDiscoveryService() {

    }
}
