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
package org.eclipse.smarthome.binding.unipievok.internal.discovery;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants;
import org.eclipse.smarthome.binding.unipievok.handler.UniPiBridgeHandler;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Dragan Gajic
 *
 */
public class UniPiDiscoveryService extends AbstractDiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(UniPiDiscoveryService.class);

    private final UniPiBridgeHandler bridge;

    public UniPiDiscoveryService(UniPiBridgeHandler bridge, int timeout) {
        super(Collections.singleton(UniPiBindingConstants.THING_TYPE_BRIDGE), timeout);
        this.bridge = bridge;
    }

    @Override
    protected void startScan() {
        if (bridge.getThing().getStatus().equals(ThingStatus.ONLINE)) {
            logger.debug("Starting UniPi scan for devices via Evok API");
            Map<Class<? extends Device>, List<Device>> types = bridge.getDeviceTypes();
            types.entrySet().stream().forEach(e -> {
                discover(e.getKey(), e.getValue());
            });
        } else {
            logger.debug("Skipping scan because bridge status is not ONLINE but {}", bridge.getThing().getStatus());
        }
    }

    private void discover(Class<? extends Device> clazz, List<Device> devices) {
        if (Sensor.class.isAssignableFrom(clazz)) {
            discoverSensors(devices);
        } else if (Digitalnput.class.isAssignableFrom(clazz)) {
            discoverDigitalInputs(devices);
        } else if (RelayOutput.class.isAssignableFrom(clazz)) {
            discoverRelayOutput(devices);
        } else {
            logger.warn("Unsupported device discovered: {}", clazz.getName());
        }
    }

    private void discoverSensors(List<Device> sensors) {
        logger.debug("{} sensors discovered:", sensors.size());

        sensors.stream().forEach(s -> {
            if (s instanceof TemperatureSensor) {
                // @formatter:off
                DiscoveryResult dr = DiscoveryResultBuilder
                        .create(new ThingUID(THING_TYPE_TEMPERATURE_SENSOR, getBridgeUID(), s.getId()))
                        .withThingType(THING_TYPE_TEMPERATURE_SENSOR)
                        .withBridge(getBridgeUID())
                        .withProperty("type", s.getProperty("typ"))
                        .withRepresentationProperty(s.getId())
                        .withLabel("Temperature sensor " + s.getProperty("typ"))
                        .build();
                // @formatter:on

                logger.debug("Temperature sensor of type {} with id {} discovered", s.getProperty("typ"), s.getId());

                thingDiscovered(dr);

            } else if (s instanceof Ds2438MultiSensor) {
                // @formatter:off
                DiscoveryResult dr = DiscoveryResultBuilder
                        .create(new ThingUID(THING_TYPE_DS2438_MULTI_SENSOR, getBridgeUID(), s.getId()))
                        .withThingType(THING_TYPE_DS2438_MULTI_SENSOR)
                        .withBridge(getBridgeUID())
                        .withProperty("type", s.getProperty("typ"))
                        .withRepresentationProperty(s.getId())
                        .withLabel("Multi sensor " + s.getProperty("typ"))
                        .build();
                // @formatter:on

                logger.debug("Multi-sensor of type {} with id {} discovered", s.getProperty("typ"), s.getId());

                thingDiscovered(dr);
            }
        });
    }

    private void discoverDigitalInputs(List<Device> inputs) {
        logger.debug("{} digital inputs discovered:", inputs.size());

        inputs.stream().forEach(di -> {
            // @formatter:off
            DiscoveryResult dr = DiscoveryResultBuilder
                    .create(new ThingUID(THING_TYPE_DIGITAL_INPUT, getBridgeUID(), di.getId()))
                    .withThingType(THING_TYPE_DIGITAL_INPUT)
                    .withBridge(getBridgeUID())
                    .withRepresentationProperty(di.getId())
                    .withLabel("Digital input " + di.getId())
                    .build();
            // @formatter:on

            logger.debug("Digital input with id {} discovered", di.getId());

            thingDiscovered(dr);
        });
    }

    private void discoverRelayOutput(List<Device> relays) {
        logger.debug("{} relay outputs discovered:", relays.size());

        relays.stream().forEach(r -> {
            // @formatter:off
            DiscoveryResult dr = DiscoveryResultBuilder
                    .create(new ThingUID(THING_TYPE_RELAY_OUTPUT, getBridgeUID(), r.getId()))
                    .withThingType(THING_TYPE_RELAY_OUTPUT)
                    .withBridge(getBridgeUID())
                    .withRepresentationProperty(r.getId())
                    .withLabel("Relay output " + r.getId())
                    .build();
            // @formatter:on

            logger.debug("Relay output with id {} discovered", r.getId());

            thingDiscovered(dr);
        });
    }

    private ThingUID getBridgeUID() {
        return bridge.getThing().getUID();
    }
}
