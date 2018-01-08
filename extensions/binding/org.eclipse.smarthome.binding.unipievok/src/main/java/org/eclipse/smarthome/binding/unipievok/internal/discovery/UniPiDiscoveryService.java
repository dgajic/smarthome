package org.eclipse.smarthome.binding.unipievok.internal.discovery;

import java.util.Collections;

import org.eclipse.smarthome.binding.unipievok.UniPiBindingConstants;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;

public class UniPiDiscoveryService extends AbstractDiscoveryService {

    public static final int DISCOVERY_TIMEOUT = 10;

    public UniPiDiscoveryService(int timeout) throws IllegalArgumentException {
        super(Collections.singleton(UniPiBindingConstants.THING_TYPE_BRIDGE), DISCOVERY_TIMEOUT);
    }

    @Override
    protected void startScan() {
        // TODO Auto-generated method stub
    }
}
