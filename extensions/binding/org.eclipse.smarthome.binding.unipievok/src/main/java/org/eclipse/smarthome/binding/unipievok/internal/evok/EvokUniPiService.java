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

import java.lang.reflect.Type;
import java.net.URI;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.util.Fields;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiService;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiServiceException;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.CompleteStateDeserializer;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.DigitalInputTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.DigitalOutputTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.Ds18b20TypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.Ds2438TypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.NeuronTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.RelayOutputTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Ds2438MultiSensor;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author Dragan Gajic
 *
 */
public class EvokUniPiService implements UniPiService {

    private final Logger logger = LoggerFactory.getLogger(EvokUniPiService.class);

    private static final Type DEVICE_COLLECTION_TYPE = new TypeToken<Collection<Device>>() {
    }.getType();

    private final HttpClient httpClient = new HttpClient();
    private final WebSocketClient wsClient = new WebSocketClient();

    private final Gson gson;

    private final String ipAddress;
    private final int port;

    private URI apiRest;

    public EvokUniPiService(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;

        // @formatter:off
        gson = new GsonBuilder()
                .registerTypeAdapter(Digitalnput.class, new DigitalInputTypeAdapter())
                .registerTypeAdapter(DigitalOutput.class, new DigitalOutputTypeAdapter())
                .registerTypeAdapter(RelayOutput.class, new RelayOutputTypeAdapter())
                .registerTypeAdapter(Neuron.class, new NeuronTypeAdapter())
                .registerTypeAdapter(TemperatureSensor.class, new Ds18b20TypeAdapter())
                .registerTypeAdapter(Ds2438MultiSensor.class, new Ds2438TypeAdapter())
                .registerTypeAdapter(new TypeToken<Collection<Device>>() {
                }.getType(), new CompleteStateDeserializer())
                .create();
        // @formatter:on
    }

    @Override
    public Device[] getState() {
        if (!httpClient.isStarted()) {
            throw new UniPiServiceException("HTTP client is not started");
        }

        try {
            ContentResponse response = httpClient.GET(apiRest.toString() + "/rest/all");

            if (response.getStatus() != HttpStatus.OK_200) {
                throw new UniPiServiceException("evokservice.getstate.status.error");
            }

            return gson.fromJson(response.getContentAsString(), DEVICE_COLLECTION_TYPE);

        } catch (Exception e) {
            throw new UniPiServiceException("evokservice.getstate.error", e);
        }
    }

    @Override
    public void setRelayState(String circuit, boolean state) {
        if (!httpClient.isStarted()) {
            throw new UniPiServiceException("HTTP client is not started");
        }

        try {
            Fields fields = new Fields();
            fields.put("value", state ? "1" : "0");
            ContentResponse response = httpClient.FORM(apiRest.toString() + "/rest/relay/" + circuit, fields);

            if (response.getStatus() != HttpStatus.OK_200) {
                throw new UniPiServiceException("evokservice.getstate.status.error");
            }

        } catch (Exception e) {
            throw new UniPiServiceException("evokservice.getstate.error", e);
        }
    }

    @Override
    public void initialize() throws Exception {
        httpClient.start();
        apiRest = new URI("http", null, ipAddress, port, null, null, null);
    }

    @Override
    public void dispose() throws Exception {
        if (httpClient.isStarted()) {
            httpClient.stop();
        }
    }

    @Override
    public boolean startWebSocketClient(Consumer<Device> eventConsumer) {

        DeviceUpdateSocketAdapter socket = new DeviceUpdateSocketAdapter(eventConsumer);

        try {
            wsClient.start();

            URI echoUri = new URI("ws", null, ipAddress, port, "/ws", null, null);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            Session session = wsClient.connect(socket, echoUri, request).get();

            session.getRemote().sendString("{\"cmd\":\"filter\", \"devices\":[\"input\"]}");

            logger.info("Web Socket client connecting to: {}", echoUri);

            // wait for closed socket connection.
            socket.await();

            return true;

        } catch (Throwable t) {
            logger.error("UniPi WebSocket client error", t);
        } finally {
            try {
                wsClient.stop();
            } catch (Exception e) {
                logger.error("Stopping UniPi WebSocket client error", e);
            }
        }
        return false;
    }

    class DeviceUpdateSocketAdapter extends WebSocketAdapter {
        private final CountDownLatch closeLatch;
        private final Consumer<Device> onMessageConsumer;

        public DeviceUpdateSocketAdapter(Consumer<Device> consumer) {
            this.closeLatch = new CountDownLatch(1);
            onMessageConsumer = consumer;
        }

        public void await() throws InterruptedException {
            this.closeLatch.await();
        }

        public void stop() throws InterruptedException {
            this.closeLatch.countDown();
        }

        @Override
        public void onWebSocketText(@Nullable String message) {
            if (onMessageConsumer != null) {
                logger.debug("WebSocket message received: {}", message);
                Device[] devices = gson.fromJson(message, DEVICE_COLLECTION_TYPE);
                Stream.of(devices).forEach(onMessageConsumer);
            } else {
                logger.debug("WebSocket message received, but not processed: {}", message);
            }
        }
    }

    @Override
    public void stopWebSocketClient() {
        try {
            wsClient.stop();
        } catch (Exception e) {
            logger.error("Stopping UniPi WebSocket client error", e);
        }
    }
}
