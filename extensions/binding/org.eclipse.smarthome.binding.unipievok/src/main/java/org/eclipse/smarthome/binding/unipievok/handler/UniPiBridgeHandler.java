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

import java.util.Collection;
import java.util.Collections;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;
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

    public UniPiBridgeHandler(Bridge bridge) {
        super(bridge);
    }

    @Override
    public void initialize() {
        logger.debug("Initialize UniPi PLC");

        final String apiUrl = getAPIUrl();

        if (apiUrl == null || apiUrl.isEmpty()) {
            updateStatus(ThingStatus.OFFLINE, ThingStatusDetail.OFFLINE.CONFIGURATION_ERROR,
                    "@text/offline.conf-error-no-ip-address");
        } else {
            // check connection
            // register discovery
            updateProperty("model", "XXX");
            updateStatus(ThingStatus.ONLINE);

        }
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

}
