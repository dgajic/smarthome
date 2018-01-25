package org.eclipse.smarthome.binding.unipievok.internal.discovery;

import static org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants.THING_TYPE_TEMPERATURE_SENSOR;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants;
import org.eclipse.smarthome.binding.unipievok.handler.UniPiBridgeHandler;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.Sensor;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;
import org.eclipse.smarthome.config.discovery.DiscoveryResult;
import org.eclipse.smarthome.config.discovery.DiscoveryResultBuilder;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.ThingUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UniPiDiscoveryService extends AbstractDiscoveryService {

    private final Logger logger = LoggerFactory.getLogger(UniPiDiscoveryService.class);

    private final UniPiBridgeHandler bridge;

    public UniPiDiscoveryService(UniPiBridgeHandler bridge, int timeout) {
        super(Collections.singleton(UniPiBindingConstants.THING_TYPE_BRIDGE), timeout);
        this.bridge = bridge;
    }

    @Override
    protected void startScan() {
        if (bridge.getThing().getStatus().equals(ThingStatus.ONLINE)) {
            logger.debug("Starting UniPi scan for devices via Evok API");
            Map<Class<? extends Device>, List<Device>> types = bridge.getDeviceTypes();
            types.entrySet().stream().forEach(e -> {
                discover(e.getKey(), e.getValue());
            });
        } else {
            logger.debug("Skipping scan because bridge status is not ONLINE but " + bridge.getThing().getStatus());
        }
    }

    private void discover(Class<? extends Device> clazz, List<Device> devices) {
        if (Sensor.class.isAssignableFrom(clazz)) {
            discoverSensors(devices);
        } else {
            logger.warn("Unsupported device discovered: {}", clazz.getName());
        }
    }

    private void discoverSensors(List<Device> sensors) {

        sensors.stream().forEach(s -> {
            if (s instanceof Sensor) {
                // @formatter:off
                DiscoveryResult dr = DiscoveryResultBuilder
                        .create(new ThingUID(THING_TYPE_TEMPERATURE_SENSOR, getBridgeUID(), s.getId()))
                        .withThingType(THING_TYPE_TEMPERATURE_SENSOR)
                        .withBridge(getBridgeUID())
                        .withProperty("type", s.getProperty("typ"))
                        .withRepresentationProperty(s.getId())
                        .withLabel("Temperature sensor Ds18b20")
                        .build();
                // @formatter:on
                thingDiscovered(dr);
            }
        });
    }

    private ThingUID getBridgeUID() {
        return bridge.getThing().getUID();
    }
}
