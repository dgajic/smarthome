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
package org.eclipse.smarthome.binding.unipievok.handler;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.API_URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiService;
import org.eclipse.smarthome.binding.unipievok.internal.evok.EvokUniPiService;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;
import org.eclipse.smarthome.config.core.status.ConfigStatusMessage;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.binding.ConfigStatusBridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link UniPiBridgeHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Dragan Gajic - Initial contribution
 */
@NonNullByDefault
public class UniPiBridgeHandler extends ConfigStatusBridgeHandler {

    private final Logger logger = LoggerFactory.getLogger(UniPiBridgeHandler.class);

    private final Map<String, UpdateListener<Sensor<?>>> sensorUpdateListeners = new ConcurrentHashMap<>();

    private final Map<String, UpdateListener<Digitalnput>> digitalInputsUpdateListeners = new ConcurrentHashMap<>();

    @Nullable
    private UniPiService uniPiService;

    public UniPiBridgeHandler(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void initialize() {
        logger.debug("Initialize UniPi Bridge handler");

        final String apiUrl = getAPIUrl();

        if (apiUrl == null || apiUrl.isEmpty()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                    "@text/offline.conf-error-no-ip-address");
        } else {
            // check connection
            uniPiService = new EvokUniPiService(apiUrl);
            try {
                uniPiService.initialize();
                schedulePoolingJob();
            } catch (Exception e) {
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                        "Unable to initialize UniPiService");
            }
        }
    }

    @SuppressWarnings("null")
    private void schedulePoolingJob() {
        scheduler.scheduleAtFixedRate(() -> {

            logger.debug("About to pull the state from ALL devices");

            try {
                // onUpdate
                final Map<Class<? extends Device>, List<Device>> update = getDeviceTypes();

                if (update == null || update.size() == 0) {
                    updateStatus(ThingStatus.UNKNOWN, ThingStatusDetail.NONE, "Empty or no state returned");
                } else {

                    logger.debug("State pulled for {} devices", update.size());

                    // notify sensor listeners
                    update.entrySet().stream().forEach(e -> {
                        if (Sensor.class.isAssignableFrom(e.getKey())) {
                            notifyListeners(id -> sensorUpdateListeners.get(id), e.getValue());
                        } else if (Digitalnput.class.isAssignableFrom(e.getKey())) {
                            notifyListeners(id -> digitalInputsUpdateListeners.get(id), e.getValue());
                        }
                    });
                    updateStatus(ThingStatus.ONLINE);
                }
            } catch (Throwable t) {
                logger.error("Error cacthed: {}", t.getMessage());
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE, "Bridge error: " + t.getMessage());
            }
        }, 0, 10, TimeUnit.SECONDS);
    }

    @SuppressWarnings({ "unused", "null", "unchecked" })
    private <T extends Device> void notifyListeners(Function<String, UpdateListener<T>> resolveListener,
            List<Device> devices) {
        devices.forEach(dev -> {

            logger.debug("Searching listener for device {} with id {}", dev.getClass().getSimpleName(), dev.getId());

            UpdateListener<T> listener = resolveListener.apply(dev.getId());

            if (listener != null) {
                logger.debug("Notifying listener for device with id {}", dev.getId());
                listener.onUpdate((T) dev);
            } else {
                logger.debug("No listener found for device with id {}", dev.getId());
            }
        });
    }

    private @Nullable String getAPIUrl() {
        return (String) getThing().getConfiguration().get(API_URL);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub
    }

    @Override
    public Collection<ConfigStatusMessage> getConfigStatus() {
        final String apiUrl = getAPIUrl();
        Collection<ConfigStatusMessage> configStatusMessages;

        // Check whether an Evok URL address is provided
        if (apiUrl == null || apiUrl.isEmpty()) {
            configStatusMessages = Collections.singletonList(ConfigStatusMessage.Builder.error(API_URL)
                    .withMessageKeySuffix("config-status.error.missing-ip-address-configuration").withArguments(API_URL)
                    .build());
        } else {
            configStatusMessages = Collections.emptyList();
        }

        return configStatusMessages;
    }

    @SuppressWarnings("null")
    public Map<Class<? extends Device>, List<Device>> getDeviceTypes() {
        return Stream.of(uniPiService.getState()).collect(Collectors.groupingBy(Device::getClass));
    }

    public List<Device> getDevices(Class<? extends Device> clazz) {
        return Optional.ofNullable(getDeviceTypes().get(clazz)).orElse(new ArrayList<>());
    }

    public void registerSensorsUpdateListener(String id, UpdateListener<Sensor<?>> updateListener) {
        sensorUpdateListeners.put(id, updateListener);
    }

    public void registerDigitalInputsUpdateListener(String id, UpdateListener<Digitalnput> updateListener) {
        digitalInputsUpdateListeners.put(id, updateListener);
    }

}
