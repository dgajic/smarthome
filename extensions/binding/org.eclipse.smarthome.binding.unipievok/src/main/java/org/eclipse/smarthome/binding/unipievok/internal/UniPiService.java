package org.eclipse.smarthome.binding.unipievok.internal;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

public interface UniPiService {

    void initialize() throws Exception;

    Device[] getState();

    void dispose() throws Exception;
}
