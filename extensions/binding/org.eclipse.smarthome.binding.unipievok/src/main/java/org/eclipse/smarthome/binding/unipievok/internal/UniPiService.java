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
package org.eclipse.smarthome.binding.unipievok.internal;

import java.util.function.Consumer;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

/**
 *
 * @author Dragan Gajic
 *
 */
public interface UniPiService {

    void initialize() throws Exception;

    Device[] getState();

    void dispose() throws Exception;

    void setRelayState(String circuit, boolean state);

    boolean startWebSocketClient(Consumer<Device> eventConsumer);

    void stopWebSocketClient();

}
