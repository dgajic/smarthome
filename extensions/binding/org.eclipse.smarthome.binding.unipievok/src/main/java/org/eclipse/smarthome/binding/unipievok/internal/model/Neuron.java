package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.Map;

public class Neuron extends Device {
    private String serialNumber;
    private String model;
    private int circuit;
    private int boardCount;

    private DigitalInputs digitalInputs;
    private RelayOutputs relayOutputs;
    private DigitalOutputs digitalOutputs;

    public Neuron(String id) {
        super(id);
    }

    public Neuron(String id, Map<String, String> properties) {
        super(id, properties);
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCircuit() {
        return circuit;
    }

    public void setCircuit(int circuit) {
        this.circuit = circuit;
    }

    public int getBoardCount() {
        return boardCount;
    }

    public void setBoardCount(int boardCount) {
        this.boardCount = boardCount;
    }

    public DigitalInputs getDigitalInputs() {
        return digitalInputs;
    }

    public void setDigitalInputs(DigitalInputs digitalInputs) {
        this.digitalInputs = digitalInputs;
    }

    public RelayOutputs getRelayOutputs() {
        return relayOutputs;
    }

    public void setRelayOutputs(RelayOutputs relayOutputs) {
        this.relayOutputs = relayOutputs;
    }

    public DigitalOutputs getDigitalOutputs() {
        return digitalOutputs;
    }

    public void setDigitalOutputs(DigitalOutputs digitalOutputs) {
        this.digitalOutputs = digitalOutputs;
    }
}
