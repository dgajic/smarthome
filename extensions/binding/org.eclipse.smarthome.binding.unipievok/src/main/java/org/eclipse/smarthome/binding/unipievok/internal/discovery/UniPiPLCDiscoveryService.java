package org.eclipse.smarthome.binding.unipievok.internal.discovery;

import java.util.Collections;

import org.eclipse.smarthome.binding.unipievok.UniPiEvokBindingConstants;
import org.eclipse.smarthome.config.discovery.AbstractDiscoveryService;

public class UniPiPLCDiscoveryService extends AbstractDiscoveryService {

    public static final int DISCOVERY_TIMEOUT = 10;

    public UniPiPLCDiscoveryService(int timeout) throws IllegalArgumentException {
        super(Collections.singleton(UniPiEvokBindingConstants.THING_TYPE_BRIDGE), DISCOVERY_TIMEOUT);
    }

    @Override
    protected void startScan() {
        // TODO Auto-generated method stub
    }
}
