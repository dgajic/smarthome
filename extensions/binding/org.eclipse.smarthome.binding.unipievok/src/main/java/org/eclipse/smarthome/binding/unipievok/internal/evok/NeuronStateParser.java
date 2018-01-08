package org.eclipse.smarthome.binding.unipievok.internal.evok;

import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;

public interface NeuronStateParser {

    Neuron parse(String data);
}
