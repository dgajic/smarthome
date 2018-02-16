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

import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;

/**
 *
 * @author Dragan Gajic
 *
 */
public class NeuronTypeAdapter extends DeviceTypeAdapter<Neuron> {

    @Override
    protected Neuron create() {
        return new Neuron();
    }

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("sn", (dev, reader) -> {
            dev.setSerialNumber(reader.nextInt());
        });

        registerHandler("model", (dev, reader) -> {
            dev.setModel(reader.nextString());
        });

        registerHandler("board_count", (dev, reader) -> {
            dev.setBoardCount(reader.nextInt());
        });

    }

}
