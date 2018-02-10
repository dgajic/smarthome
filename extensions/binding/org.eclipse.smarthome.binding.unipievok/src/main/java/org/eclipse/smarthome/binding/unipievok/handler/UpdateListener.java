package org.eclipse.smarthome.binding.unipievok.handler;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;

public interface UpdateListener<T extends Device> {

    void onUpdate(@NonNull T device);
}
