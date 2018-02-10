package org.eclipse.smarthome.binding.unipievok.handler;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.TEMPERATURE;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;

@NonNullByDefault
public class UniPiTemperatureSensorsHandler extends UniPiAbstractHandler<TemperatureSensor> {

    public UniPiTemperatureSensorsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void onUpdate(TemperatureSensor temp) {
        if (temp.getValue() != null) {
            updateState(new ChannelUID(getThing().getUID(), TEMPERATURE), new DecimalType(temp.getValue()));
            logger.debug("Temperature updated for {}, with value {}", temp.getId(), temp.getValue());
        } else {
            logger.warn("Temperature for sensor {} is null", temp.getId());
        }
    }
}
