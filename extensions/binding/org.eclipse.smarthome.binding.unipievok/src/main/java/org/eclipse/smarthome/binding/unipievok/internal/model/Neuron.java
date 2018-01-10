package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.Map;

public class Neuron extends Device {
    private Integer serialNumber;
    private String model;
    private String circuit;
    private Integer boardCount;

    private MultiBinaryDevices<DigitalnputDevice> digitalInputs = new MultiBinaryDevices<>("digitalInputs");
    private MultiBinaryDevices<RelayOutputDevice> relayOutputs = new MultiBinaryDevices<>("relayOutputs");
    private MultiBinaryDevices<DigitalOutputDevice> digitalOutputs = new MultiBinaryDevices<>("digitalOutputs");

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

    public MultiBinaryDevices<DigitalnputDevice> getDigitalInputs() {
        return digitalInputs;
    }

    public void setDigitalInputs(MultiBinaryDevices<DigitalnputDevice> digitalInputs) {
        this.digitalInputs = digitalInputs;
    }

    public MultiBinaryDevices<RelayOutputDevice> getRelayOutputs() {
        return relayOutputs;
    }

    public void setRelayOutputs(MultiBinaryDevices<RelayOutputDevice> relayOutputs) {
        this.relayOutputs = relayOutputs;
    }

    public MultiBinaryDevices<DigitalOutputDevice> getDigitalOutputs() {
        return digitalOutputs;
    }

    public void setDigitalOutputs(MultiBinaryDevices<DigitalOutputDevice> digitalOutputs) {
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
