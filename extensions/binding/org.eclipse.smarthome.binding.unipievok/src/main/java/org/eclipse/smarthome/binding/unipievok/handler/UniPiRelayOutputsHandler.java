package org.eclipse.smarthome.binding.unipievok.handler;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.types.Command;

@NonNullByDefault
public class UniPiRelayOutputsHandler extends UniPiAbstractHandler<RelayOutput> {

    public UniPiRelayOutputsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void onUpdate(RelayOutput relayOutput) {
        OnOffType state = relayOutput.isSet() ? OnOffType.ON : OnOffType.OFF;
        updateState(new ChannelUID(getThing().getUID(), "relay"), state);
        logger.debug("Relay output updated to {}, for device with id {}", state, relayOutput.getId());
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        getBridgeHandler().ifPresent(handler -> {
            String circuit = channelUID.getThingUID().getId();
            boolean state = false;
            if (command instanceof OnOffType) {
                OnOffType cmd = (OnOffType) command;
                state = OnOffType.ON == cmd;
            }
            handler.getUniPiService().setRelayState(circuit, state);
        });
    }
}
