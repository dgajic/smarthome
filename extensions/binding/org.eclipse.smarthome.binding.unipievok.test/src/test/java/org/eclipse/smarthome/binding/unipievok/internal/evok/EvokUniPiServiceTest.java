package org.eclipse.smarthome.binding.unipievok.internal.evok;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.junit.Before;
import org.junit.Test;

public class EvokUniPiServiceTest {

    private EvokUniPiService evokService;

    @Before
    public void setUp() throws Exception {
        evokService = new EvokUniPiService("http://192.168.1.104:88");
    }

    @Test
    public void getStateTest() throws Exception {
        Collection<Device> devices = evokService.getState();
        assertNotNull(devices);
    }

}
