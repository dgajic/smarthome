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

public abstract class BinaryDevice extends Device {

    private boolean state;

    public BinaryDevice() {
        super();
    }

    public BinaryDevice(String id, boolean state) {
        super(id);
        this.state = state;
    }

    public boolean isSet() {
        return state;
    }

    public void set() {
        state = true;
    }

    public void set(boolean state) {
        this.state = state;
    }

    public void reset() {
        state = false;
    }
}
