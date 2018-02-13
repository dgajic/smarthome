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

import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;

public class DigitalInputTypeAdapter extends DeviceTypeAdapter<Digitalnput> {

    @Override
    protected Digitalnput create() {
        return new Digitalnput();
    }

    @Override
    protected void registerAdditionalHandlers() {
        registerHandler("value", (dev, reader) -> {
            dev.set(reader.nextInt() != 0);
        });

        registerHandler("debounce", (dev, reader) -> {
            dev.setDebounce(reader.nextInt());
        });

        registerHandler("counter", (dev, reader) -> {
            dev.setCounter(reader.nextInt());
        });

        registerHandler("counter_mode", (dev, reader) -> {
            dev.setCounterMode(reader.nextString());
        });
    }

}
