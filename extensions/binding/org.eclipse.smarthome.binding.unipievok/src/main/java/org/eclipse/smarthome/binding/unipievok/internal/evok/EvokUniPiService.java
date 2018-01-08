package org.eclipse.smarthome.binding.unipievok.internal.evok;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiService;
import org.eclipse.smarthome.binding.unipievok.internal.UniPiServiceException;
import org.eclipse.smarthome.binding.unipievok.internal.model.Neuron;

public class EvokUniPiService implements UniPiService {

    private final HttpClient httpClient = new HttpClient();
    private final NeuronStateParser stateParser;

    private String url;

    public EvokUniPiService(String url) {
        this.url = url;
        this.stateParser = new GsonNeuronStateParser();
    }

    @Override
    public Neuron getState() {
        try {
            ContentResponse response = httpClient.GET(url + "/json/all");

            if (response.getStatus() != HttpStatus.OK_200) {
                throw new UniPiServiceException("evokservice.getstate.status.error");
            }

            return stateParser.parse(response.getContentAsString());

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
    public void initialize() {
        try {
            httpClient.start();
        } catch (Exception e) {
            throw new UniPiServiceException("evokservice.initialize.error", e);
        }
    }

    @Override
    public void dispose() {
        try {
            httpClient.stop();
        } catch (Exception e) {
            throw new UniPiServiceException("evokservice.dispose.error", e);
        }
    }

}
