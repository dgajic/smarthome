package org.eclipse.smarthome.binding.unipievok.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;

@NonNullByDefault
public class UniPiDigitalInputsHandler extends UniPiAbstractHandler<Digitalnput> {

    public UniPiDigitalInputsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void onUpdate(Digitalnput digitalInput) {
        OnOffType state = digitalInput.isSet() ? OnOffType.ON : OnOffType.OFF;
        updateState(new ChannelUID(getThing().getUID(), "dinput"), state);
        logger.debug("Digital input updated to {}, for device with id {}", state, digitalInput.getId());
    }

}
