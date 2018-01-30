package org.eclipse.smarthome.binding.unipievok.handler;

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;
import org.eclipse.smarthome.core.library.types.OpenClosedType;
import org.eclipse.smarthome.core.thing.Bridge;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.thing.binding.BridgeHandler;
import org.eclipse.smarthome.core.types.Command;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@NonNullByDefault
public class UniPiDigitalInputsHandler extends BaseThingHandler implements UpdateListener<Digitalnput> {

    private final Logger logger = LoggerFactory.getLogger(UniPiDigitalInputsHandler.class);

    public UniPiDigitalInputsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        logger.debug("Initialize UniPi Digital inputs handler");
        Optional<UniPiBridgeHandler> handler = getBridgeHandler();
        if (!handler.isPresent()) {
            updateStatus(ThingStatus.OFFLINE);
        } else {
            handler.get().registerDigitalInputsUpdateListener(getThing().getUID().getId(), this);
            updateStatus(ThingStatus.ONLINE);
            logger.debug("Registered as UpdateListener for thing {}", getThing().getUID());
        }
    }

    @Override
    public void handleCommand(@NonNull ChannelUID channelUID, @NonNull Command command) {
        // TODO refresh
    }

    @Override
    public boolean isSupported(Class<? extends Device> clazz) {
        return Sensor.class.isAssignableFrom(clazz);
    }

    private Optional<UniPiBridgeHandler> getBridgeHandler() {
        Bridge bridge = getBridge();
        if (bridge != null) {
            BridgeHandler handler = bridge.getHandler();
            if (handler != null && handler instanceof UniPiBridgeHandler) {
                return Optional.of((UniPiBridgeHandler) handler);
            }
        }
        return Optional.empty();
    }

    @Override
    public void onUpdate(Digitalnput digitalInput) {
        OpenClosedType state = digitalInput.isSet() ? OpenClosedType.CLOSED : OpenClosedType.OPEN;
        updateState(new ChannelUID(getThing().getUID(), "dinput"), state);
        logger.debug("Digital input updated to {}, for device with id {}", state, digitalInput.getId());
    }
}
