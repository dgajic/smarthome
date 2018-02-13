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
package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.HashMap;
import java.util.Map;

public class MultiValue {
    private final Map<String, Class<?>> definition;
    private final Map<String, Object> values = new HashMap<>();

    private MultiValue(Builder builder) {
        this.definition = builder.definition;
    }

    public static class Builder {
        private final Map<String, Class<?>> definition = new HashMap<>();

        public Builder declare(String name, Class<?> type) {
            definition.put(name, type);
            return this;
        }

        public MultiValue build() {
            return new MultiValue(this);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String name, Class<T> clazz) {
        validate(name, clazz);
        return (T) values.get(name);
    }

    public <T> void set(String name, T value) {
        validate(name, value.getClass());
        values.put(name, value);
    }

    private void validate(String name, Class<?> clazz) {
        Class<?> type = definition.get(name);

        if (type == null) {
            throw new IllegalArgumentException("Unknown field " + name);
        }

        if (clazz != type) {
            throw new IllegalArgumentException("Field " + name + " is defined as " + type.getSimpleName()
                    + " but passed value is " + clazz.getSimpleName());
        }
    }
}
