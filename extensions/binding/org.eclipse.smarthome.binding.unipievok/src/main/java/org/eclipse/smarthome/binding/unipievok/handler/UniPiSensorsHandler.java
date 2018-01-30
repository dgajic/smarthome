package org.eclipse.smarthome.binding.unipievok.handler;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.eclipse.smarthome.core.library.types.DecimalType;
import org.eclipse.smarthome.core.library.types.PercentType;
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
public class UniPiSensorsHandler extends BaseThingHandler implements UpdateListener<Sensor<?>> {

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
            handler.get().registerSensorsUpdateListener(getThing().getUID().getId(), this);
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
    public void onUpdate(Sensor<?> sensor) {
        if (sensor instanceof TemperatureSensor) {
            onUpdateTemperature((TemperatureSensor) sensor);
        } else if (sensor instanceof Ds2438MultiSensor) {
            onUpdateDs2438((Ds2438MultiSensor) sensor);
        }
    }

    private void onUpdateTemperature(TemperatureSensor temp) {
        if (temp.getValue() != null) {
            updateState(new ChannelUID(getThing().getUID(), TEMPERATURE), new DecimalType(temp.getValue()));
            logger.debug("Temperature updated for {}, with value {}", temp.getId(), temp.getValue());
        } else {
            logger.warn("Temperature for sensor {} is null", temp.getId());
        }
    }

    private void onUpdateDs2438(Ds2438MultiSensor sen) {
        if (sen.getTemperature() != null) {
            updateState(new ChannelUID(getThing().getUID(), TEMPERATURE), new DecimalType(sen.getTemperature()));
            logger.debug("Temperature updated for {}, with value {}", sen.getId(), sen.getTemperature());
        }

        if (sen.getHumidity() != null) {
            updateState(new ChannelUID(getThing().getUID(), HUMIDITY),
                    new PercentType(BigDecimal.valueOf(sen.getHumidity())));
            logger.debug("Humidity updated for {}, with value {}", sen.getId(), sen.getHumidity());
        }

        if (sen.getIlluminance() != null) {
            updateState(new ChannelUID(getThing().getUID(), ILLUMINANCE), new DecimalType(sen.getIlluminance()));
            logger.debug("Illuminance updated for {}, with value {}", sen.getId(), sen.getIlluminance());
        }
    }
}
