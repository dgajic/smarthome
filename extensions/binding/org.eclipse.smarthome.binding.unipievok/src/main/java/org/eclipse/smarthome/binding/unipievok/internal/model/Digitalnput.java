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

public class Digitalnput extends BinaryDevice {
    private int debounce;
    private Integer counter;
    private String counterMode;

    public int getDebounce() {
        return debounce;
    }

    public void setDebounce(int debounce) {
        this.debounce = debounce;
    }

    public Integer getCounter() {
        return counter;
    }

    public void setCounter(Integer counter) {
        this.counter = counter;
    }

    public String getCounterMode() {
        return counterMode;
    }

    public void setCounterMode(String counterMode) {
        this.counterMode = counterMode;
    }
}