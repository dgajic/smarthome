package org.eclipse.smarthome.binding.unipievok.internal;

import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;

public interface UniPiService {

    void initialize();

    Neuron getState();

    void dispose();
}
