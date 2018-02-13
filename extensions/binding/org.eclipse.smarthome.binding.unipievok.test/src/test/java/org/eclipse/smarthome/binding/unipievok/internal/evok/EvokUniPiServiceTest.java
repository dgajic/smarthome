/**
 * Copyright (c) 2014,2018 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.binding.unipievok.internal.evok;

import static org.junit.Assert.assertNotNull;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EvokUniPiServiceTest {

    private EvokUniPiService evokService;

    private WebSocketClient client;

    @Before
    public void setUp() throws Exception {
        evokService = new EvokUniPiService("192.168.1.104", 80);
        evokService.initialize();

        client = new WebSocketClient();
    }

    @Test
    public void getStateTest() throws Exception {
        Device[] devices = evokService.getState();
        assertNotNull(devices);
    }

    @Test
    public void getWebSocketTest() {

        SimpleEchoSocket socket = new SimpleEchoSocket();
        try {
            client.start();

            URI echoUri = new URI("ws://192.168.1.104/ws");
            ClientUpgradeRequest request = new ClientUpgradeRequest();

            Session session = client.connect(socket, echoUri, request).get();

            session.getRemote().sendString("{\"cmd\":\"filter\", \"devices\":[\"input\"]}");

            System.out.printf("Connecting to : %s%n", echoUri);

            // wait for closed socket connection.
            socket.awaitClose(20, TimeUnit.SECONDS);

        } catch (Throwable t) {
            t.printStackTrace();
        } finally {
            try {
                client.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    class SimpleEchoSocket extends WebSocketAdapter {
        private final CountDownLatch closeLatch;
        @SuppressWarnings("unused")
        private Session session;

        public SimpleEchoSocket() {
            this.closeLatch = new CountDownLatch(1);
        }

        public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException {
            return this.closeLatch.await(duration, unit);
        }

        public void configure() {
            if (isConnected()) {
                // getRemote().sendString("");
            }
        }

        @Override
        public void onWebSocketText(String message) {
            System.out.println(message);
        }

    }

    @After
    public void tearDown() throws Exception {
        evokService.dispose();
    }
}
