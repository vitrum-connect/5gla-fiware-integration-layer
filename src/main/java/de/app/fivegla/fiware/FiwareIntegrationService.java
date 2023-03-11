package de.app.fivegla.fiware;

import com.google.gson.Gson;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.request.UpdateOrCreateEntityRequest;
import lombok.extern.java.Log;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
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
    @SuppressWarnings("unused")
    public String getCurrentVersion() {
        return properties.getProperty(PropertyKeys.APP_VERSION);
    }

    /**
     * Creates a new device in the context broker.
     *
     * @param device the device to create
     */
    public void persist(Device device) {
        var updateOrCreateEntityRequest = UpdateOrCreateEntityRequest.builder()
                .entities(List.of(device))
                .build();
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/op/update"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(updateOrCreateEntityRequest))).build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                log.log(Level.SEVERE, "Could not create device. Response: " + response.body());
                log.log(Level.FINE, "Request: " + GSON.toJson(updateOrCreateEntityRequest));
                log.log(Level.FINE, "Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not create device, there was an error from FIWARE.");
            } else {
                log.log(Level.INFO, "Device created/updated successfully.");
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not create/update device.", e);
            throw new FiwareIntegrationLayerException("Could not create/update device");
        }
    }

    /**
     * Checks if a device with the given id exists.
     *
     * @param deviceId the device to check
     * @return true if the device exists, false otherwise
     */
    public boolean exists(String deviceId) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/entities/" + deviceId))
                .GET().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.log(Level.WARNING, String.format("Device with the ID '%s' does not exist.", deviceId));
                log.log(Level.FINE, "Response: " + response.body());
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not check if device exists.", e);
            throw new FiwareIntegrationLayerException("Could not check if device exists.");
        }
    }

    /**
     * Returns the device with the given id.
     *
     * @param deviceId the id of the device
     * @return the device
     */
    public Optional<Device> readDevice(String deviceId) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/entities/" + deviceId))
                .GET().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.log(Level.WARNING, String.format("Device with the ID '%s' does not exist.", deviceId));
                log.log(Level.FINE, "Response: " + response.body());
                return Optional.empty();
            } else {
                log.log(Level.INFO, "Device read successfully.");
                log.log(Level.FINE, "Response: " + response.body());
                return Optional.of(GSON.fromJson(response.body(), Device.class));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Could not read the device.", e);
            throw new FiwareIntegrationLayerException("Could not read the device.");
        }
    }

}
