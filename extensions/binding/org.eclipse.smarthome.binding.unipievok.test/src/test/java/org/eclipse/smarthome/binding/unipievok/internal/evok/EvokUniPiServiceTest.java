package org.eclipse.smarthome.binding.unipievok.internal.evok;

import static org.junit.Assert.assertNotNull;

import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.junit.Before;
import org.junit.Test;

public class EvokUniPiServiceTest {

    private EvokUniPiService evokService;

    @Before
    public void setUp() {
        evokService = new EvokUniPiService("http://192.168.1.104:88");
        evokService.initialize();
    }

    @Test
    public void getStateTest() throws Exception {
        Neuron neuron = evokService.getState();
        assertNotNull(neuron);
    }
}
