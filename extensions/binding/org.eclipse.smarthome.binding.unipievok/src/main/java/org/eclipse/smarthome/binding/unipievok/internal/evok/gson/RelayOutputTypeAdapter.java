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
package org.eclipse.smarthome.binding.unipievok.internal.evok.gson;

import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;

/**
 *
 * @author Dragan Gajic
 *
 */
public class RelayOutputTypeAdapter extends DeviceTypeAdapter<RelayOutput> {

    @Override
    protected RelayOutput create() {
        return new RelayOutput();
    }

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("value", (dev, reader) -> {
            dev.set(reader.nextInt() != 0);
        });

        registerHandler("pending", (dev, reader) -> {
            dev.setPending(reader.nextBoolean());
        });

    }
}
