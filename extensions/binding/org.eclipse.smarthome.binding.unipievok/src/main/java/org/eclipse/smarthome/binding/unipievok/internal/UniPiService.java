package org.eclipse.smarthome.binding.unipievok.internal;

import java.util.Collection;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

public interface UniPiService {

    Collection<Device> getState();
}
