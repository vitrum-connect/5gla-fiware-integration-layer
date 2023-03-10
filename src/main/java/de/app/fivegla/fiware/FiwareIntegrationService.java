package de.app.fivegla.fiware;

import com.google.gson.Gson;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.Device;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Log
public class FiwareIntegrationService {

    static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();
    private final String contextBrokerUrl;
    private Properties properties;

    public FiwareIntegrationService(String contextBrokerUrl) {
        this.contextBrokerUrl = contextBrokerUrl;
        initProperties();
    }

    private void initProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/package.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load package.properties", e);
        }
    }

    /**
     * Returns the current version of the package.
     *
     * @return the current version
     */
    public String getCurrentVersion() {
        return properties.getProperty(PropertyKeys.APP_VERSION);
    }

    /**
     * Creates a new device in the context broker.
     *
     * @param device the device to create
     */
    public void createDevice(Device device) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/entities"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(device))).build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 201) {
                log.log(Level.SEVERE, "Could not create device. Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not create device, there was an error from FIWARE.");
            } else {
                log.log(Level.INFO, "Device created successfully.");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not create device.", e);
            throw new FiwareIntegrationLayerException("Could not create device");
        }
    }

}
