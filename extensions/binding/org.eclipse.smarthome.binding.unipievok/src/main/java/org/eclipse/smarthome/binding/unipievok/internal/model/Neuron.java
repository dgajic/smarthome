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
