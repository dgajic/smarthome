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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.smarthome.binding.unipievok.DeviceToThingTypeMapper;
import org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiService;
import org.eclipse.smarthome.binding.unipievok.internal.evok.EvokUniPiService;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.config.core.status.ConfigStatusMessage;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingStatusDetail;
import org.eclipse.smarthome.core.thing.ThingTypeUID;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.eclipse.smarthome.core.thing.binding.ConfigStatusBridgeHandler;
import org.eclipse.smarthome.core.thing.binding.ThingHandler;
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

    private static final int DEFAULT_POLLING_INTERVAL = 30;

    private static final int DEFAULT_PORT = 80;

    private final Logger logger = LoggerFactory.getLogger(UniPiBridgeHandler.class);

    @Nullable
    private UniPiService uniPiService;

    public UniPiBridgeHandler(Bridge bridge) {
        super(bridge);
    }

    @SuppressWarnings("null")
    @Override
    public void initialize() {
        logger.debug("Initialize UniPi Bridge handler");

        // check connection
        uniPiService = new EvokUniPiService(getAPIIpAddress(), getAPIPort());
        try {
            uniPiService.initialize();
            schedulePoolingJob();
            // schedule web socket listener in the new thread
            scheduler.schedule(() -> uniPiService.startWebSocketClient(
                    device -> getUpdateListener(device).ifPresent(listener -> notifyListener(listener, device))), 1,
                    TimeUnit.SECONDS);
        } catch (Exception e) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                    "Unable to initialize UniPiService: " + e.getMessage());
        }
    }

    /**
     * This is the main pooling job, which uses configuration parameter {@link UniPiBindingConstants.POLLING_INTERVAL}
     * to poll state of all UniPi devices. After poll, for each device {@link UpdateListener} is invoked to reflect the
     * state.
     */
    @SuppressWarnings("null")
    private void schedulePoolingJob() {
        int poollingInterval = getPoollingInterval();
        logger.info("Starting pooling job with poolling interval {} sec", poollingInterval);
        scheduler.scheduleAtFixedRate(() -> {

            logger.debug("About to pull the state from ALL devices");

            try {

                Device[] update = uniPiService.getState();

                if (update == null || update.length == 0) {
                    updateStatus(ThingStatus.UNKNOWN, ThingStatusDetail.NONE, "Empty or no state returned");
                } else {

                    logger.debug("State pulled for {} devices", update.length);

                    updateStatus(ThingStatus.ONLINE);

                    Stream.of(update).forEach(device -> getUpdateListener(device)
                            .ifPresent(listener -> notifyListener(listener, device)));

                }
            } catch (Throwable t) {
                logger.error("Error cacthed: {}", t.getMessage());
                updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.BRIDGE_OFFLINE, "Bridge error: " + t.getMessage());
            }
        }, 0, poollingInterval, TimeUnit.SECONDS);
    }

    /**
     * Device is uniquely identified by type (input, relay,...) and device id.
     * This combination forms ThingUID which can be used to find thing for the device,
     * and handler which typically implements {@link UpdateListener}.
     *
     * @param device
     * @return {@link Optional} of {@link UpdateListener} of the thing for {@link Device}.
     */
    private Optional<UpdateListener<?>> getUpdateListener(Device device) {
        ThingTypeUID thingType = DeviceToThingTypeMapper.mapToThingTypeUID(device);
        if (thingType != null) {
            Thing thing = getThingByUID(new ThingUID(thingType, getThing().getUID(), device.getId()));
            if (thing != null) {
                ThingHandler handler = thing.getHandler();
                if (handler != null && handler instanceof UpdateListener<?>) {
                    return Optional.of((UpdateListener<?>) handler);
                }
            }
        }
        return Optional.empty();
    }

    @SuppressWarnings({ "unchecked" })
    private <T extends Device> void notifyListener(UpdateListener<T> listener, Device dev) {
        logger.debug("Notifying listener for device with id {}", dev.getId());
        listener.onUpdate((T) dev);
    }

    private @Nullable String getAPIIpAddress() {
        return (String) getThing().getConfiguration().get(API_IP_ADDRESS);
    }

    private int getAPIPort() {
        return getSafeInt(API_PORT, DEFAULT_PORT);
    }

    private int getPoollingInterval() {
        return getSafeInt(POLLING_INTERVAL, DEFAULT_POLLING_INTERVAL);
    }

    private int getSafeInt(String key, int def) {
        Object val = getThing().getConfiguration().get(key);
        if (val != null && val instanceof Number) {
            return ((Number) val).intValue();
        }
        logger.warn("Unable to load configuration value for {}, using default {}", key, def);
        return def;
    }

    @Override
    public Collection<ConfigStatusMessage> getConfigStatus() {
        final String ipAddress = getAPIIpAddress();
        final Collection<ConfigStatusMessage> configStatusMessages = new ArrayList<>();

        // Check whether an Evok API IP address is provided
        if (ipAddress == null || ipAddress.isEmpty()) {
            configStatusMessages.add(ConfigStatusMessage.Builder.error(API_IP_ADDRESS)
                    .withMessageKeySuffix("config-status.error.missing-ip-address-configuration")
                    .withArguments(API_IP_ADDRESS).build());
        }

        return Collections.unmodifiableCollection(configStatusMessages);
    }

    @SuppressWarnings("null")
    public Map<Class<? extends Device>, List<Device>> getDeviceTypes() {
        return Stream.of(uniPiService.getState()).collect(Collectors.groupingBy(Device::getClass));
    }

    public List<Device> getDevices(Class<? extends Device> clazz) {
        return Optional.ofNullable(getDeviceTypes().get(clazz)).orElse(new ArrayList<>());
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        // TODO Auto-generated method stub
    }

    @Nullable
    public UniPiService getUniPiService() {
        return this.uniPiService;
    }

    @Override
    public void handleConfigurationUpdate(Map<String, Object> configurationParameters) {
        // TODO Auto-generated method stub
        super.handleConfigurationUpdate(configurationParameters);
    }

    @SuppressWarnings("null")
    @Override
    public void dispose() {
        if (uniPiService != null) {
            uniPiService.stopWebSocketClient();
            try {
                uniPiService.dispose();
            } catch (Exception e) {
                logger.error("UniPiBridgeHandler dispose error:", e);
            }
        }
        super.dispose();
    }
}
