package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.Map;

public class Neuron extends Device {
    private String serialNumber;
    private String model;
    private String circuit;
    private int boardCount;

    private DigitalInputs digitalInputs = new DigitalInputs();
    private RelayOutputs relayOutputs = new RelayOutputs();
    private DigitalOutputs digitalOutputs = new DigitalOutputs();

    public Neuron() {
        super();
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

    public String getCircuit() {
        return circuit;
    }

    public void setCircuit(String circuit) {
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

    public void simpleCopy(Neuron neuron) {
        this.setId(neuron.getId());
        this.serialNumber = neuron.serialNumber;
        this.boardCount = neuron.boardCount;
        this.circuit = neuron.circuit;
        this.model = neuron.model;
    }
}
