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

import java.util.function.BiConsumer;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

import com.google.gson.stream.JsonReader;

/**
 *
 * @author Dragan Gajic
 *
 * @param <T>
 */
@FunctionalInterface
public interface DeviceFieldHandler<T extends Device> extends BiConsumer<T, JsonReader> {

    @Override
    default void accept(T dev, JsonReader reader) {
        try {
            acceptThrows(dev, reader);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    void acceptThrows(T dev, JsonReader reader) throws Exception;
}
