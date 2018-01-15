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