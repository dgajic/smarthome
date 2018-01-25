package org.eclipse.smarthome.binding.unipievok.internal.evok;

import java.lang.reflect.Type;
import java.util.Collection;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiService;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiServiceException;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.CompleteStateDeserializer;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.DigitalInputTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.DigitalOutputTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.NeuronTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.RelayOutputTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.evok.gson.TemperatureSensorTypeAdapter;
import org.eclipse.smarthome.binding.unipievok.internal.model.Device;
import org.eclipse.smarthome.binding.unipievok.internal.model.DigitalOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Digitalnput;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;
import org.eclipse.smarthome.binding.unipievok.internal.model.RelayOutput;
import org.eclipse.smarthome.binding.unipievok.internal.model.TemperatureSensor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class EvokUniPiService implements UniPiService {

    private final HttpClient httpClient = new HttpClient();
    private final Gson gson;

    private String url;

    public EvokUniPiService(String url) {
        this.url = url;
        // @formatter:off
        gson = new GsonBuilder()
                .registerTypeAdapter(Digitalnput.class, new DigitalInputTypeAdapter())
                .registerTypeAdapter(DigitalOutput.class, new DigitalOutputTypeAdapter())
                .registerTypeAdapter(RelayOutput.class, new RelayOutputTypeAdapter())
                .registerTypeAdapter(Neuron.class, new NeuronTypeAdapter())
                .registerTypeAdapter(TemperatureSensor.class, new TemperatureSensorTypeAdapter())
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
            ContentResponse response = httpClient.GET(url + "/rest/all");

            if (response.getStatus() != HttpStatus.OK_200) {
                throw new UniPiServiceException("evokservice.getstate.status.error");
            }
            Type type = new TypeToken<Collection<Device>>() {
            }.getType();

            return gson.fromJson(response.getContentAsString(), type);

        } catch (Exception e) {
            throw new UniPiServiceException("evokservice.getstate.error", e);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void initialize() throws Exception {
        httpClient.start();
    }

    @Override
    public void dispose() throws Exception {
        if (httpClient.isStarted()) {
            httpClient.stop();
        }
    }
}
