package de.app.fivegla.fiware;

import com.google.gson.Gson;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.api.Validatable;
import de.app.fivegla.fiware.request.UpdateOrCreateEntityRequest;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

/**
 * Abstract integration service.
 */
@Slf4j
public abstract class AbstractIntegrationService<T extends Validatable> {

    static final Gson GSON = new Gson().newBuilder().setPrettyPrinting().create();
    private final String contextBrokerUrl;

    public AbstractIntegrationService(String contextBrokerUrl) {
        this.contextBrokerUrl = contextBrokerUrl;
    }

    /**
     * Creates a new device in the context broker.
     *
     * @param entity the device to create
     */
    public void persist(T entity) {
        entity.validate();
        var updateOrCreateEntityRequest = UpdateOrCreateEntityRequest.builder()
                .entities(List.of(entity))
                .build();
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/op/update" + "?options=keyValues"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(GSON.toJson(updateOrCreateEntityRequest))).build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                log.error("Could not create entity. Response: " + response.body());
                log.debug("Request: " + GSON.toJson(updateOrCreateEntityRequest));
                log.debug("Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not create entity, there was an error from FIWARE.");
            } else {
                log.info("Device created/updated successfully.");
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not create/update entity");
        }
    }

    /**
     * Checks if a device with the given id exists.
     *
     * @param id the device to check
     * @return true if the device exists, false otherwise
     */
    public boolean exists(String id) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/entities/" + id))
                .GET().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.warn("Device with the ID '{}' does not exist.", id);
                log.debug("Response: " + response.body());
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not check if device exists.");
        }
    }

    /**
     * Returns the device with the given id.
     *
     * @param id the id of the device
     * @return the device
     */
    public Optional<T> read(String id) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/entities/" + id + "?options=keyValues"))
                .GET().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.warn("Device with the ID '{}' does not exist.", id);
                log.debug("Response: " + response.body());
                return Optional.empty();
            } else {
                log.info("Device read successfully.");
                log.debug("Response: " + response.body());
                return Optional.of(parseResponse(response.body()));
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not read the device.");
        }
    }

    /**
     * Parses the response from the context broker.
     *
     * @return the specific entity
     */
    abstract T parseResponse(String responseBody);

}
