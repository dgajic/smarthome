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

import java.util.Map;

public class Neuron extends Device {
    private Integer serialNumber;
    private String model;
    private String circuit;
    private Integer boardCount;

    public Neuron() {
        super();
    }

    public Neuron(String id, Map<String, String> properties) {
        super(id, properties);
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
        this.circuit = circuit;
    }

    public Integer getBoardCount() {
        return boardCount;
    }

    public void setBoardCount(Integer boardCount) {
        this.boardCount = boardCount;
    }
}
