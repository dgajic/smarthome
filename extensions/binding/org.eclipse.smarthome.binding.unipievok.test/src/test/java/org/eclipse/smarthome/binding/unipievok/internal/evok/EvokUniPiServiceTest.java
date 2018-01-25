package org.eclipse.smarthome.binding.unipievok.internal.evok;

import static org.junit.Assert.assertNotNull;

import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EvokUniPiServiceTest {

    private EvokUniPiService evokService;

    @Before
    public void setUp() throws Exception {
        evokService = new EvokUniPiService("192.168.1.104:88");
        evokService.initialize();
    }

    @Test
    public void getStateTest() throws Exception {
        Device[] devices = evokService.getState();
        assertNotNull(devices);
    }

    @After
    public void tearDown() throws Exception {
        evokService.dispose();
    }
}
