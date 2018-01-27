package org.eclipse.smarthome.binding.unipievok.internal.model;

import java.util.Set;

public abstract class MultiSensor extends Sensor<MultiValue> {

    public abstract Set<String> supportedFields();
}
