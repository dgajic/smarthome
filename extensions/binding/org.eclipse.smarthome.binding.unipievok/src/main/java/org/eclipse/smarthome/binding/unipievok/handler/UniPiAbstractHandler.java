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

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.BridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TBD
 *
 * @author Dragan Gajic
 *
 * @param <T>
 */
public abstract class UniPiAbstractHandler<T extends Device> extends BaseThingHandler implements UpdateListener<T> {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    public UniPiAbstractHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        logger.debug("Initialize UniPi {} handler", getClass().getSimpleName());
        Optional<UniPiBridgeHandler> handler = getBridgeHandler();
        if (!handler.isPresent()) {
            updateStatus(ThingStatus.OFFLINE);
        } else {
            updateStatus(ThingStatus.ONLINE);
        }
    }

    /**
     * Returns {@link UniPiBridgeHandler}.
     *
     * @return
     */
    protected Optional<UniPiBridgeHandler> getBridgeHandler() {
        Bridge bridge = getBridge();
        if (bridge != null) {
            BridgeHandler handler = bridge.getHandler();
            if (handler != null && handler instanceof UniPiBridgeHandler) {
                return Optional.of((UniPiBridgeHandler) handler);
            }
        }
        return Optional.empty();
    }

    @Override
    public void handleCommand(@NonNull ChannelUID channelUID, @NonNull Command command) {
        logger.debug("handleCommand: {} - {}", channelUID, command);
    }

}
