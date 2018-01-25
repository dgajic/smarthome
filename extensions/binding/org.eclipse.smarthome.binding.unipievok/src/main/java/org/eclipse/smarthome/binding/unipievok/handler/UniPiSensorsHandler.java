package org.eclipse.smarthome.binding.unipievok.handler;

import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.eclipse.smarthome.core.library.types.DecimalType;
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
public class UniPiSensorsHandler extends BaseThingHandler implements UpdateListener<TemperatureSensor> {

    private final Logger logger = LoggerFactory.getLogger(UniPiSensorsHandler.class);

    public UniPiSensorsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        logger.debug("Initialize UniPi Sensors handler");
        Optional<UniPiBridgeHandler> handler = getBridgeHandler();
        if (!handler.isPresent()) {
            updateStatus(ThingStatus.OFFLINE);
        } else {
            handler.get().registerUpdateListener(getThing().getUID().getId(), this);
            updateStatus(ThingStatus.ONLINE);
            logger.debug("Registered as UpdateListener");
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
    public void onUpdate(TemperatureSensor temp) {
        if (temp.getValue() != null) {
            updateState(new ChannelUID(getThing().getUID(), "temperature"),
                    new DecimalType(temp.getValue().doubleValue()));
            logger.debug("Temperature updated for {}, with value {}", temp.getId(), temp.getValue());
        } else {
            logger.warn("Temperature for sensor {} is null", temp.getId());
        }
    }
}
