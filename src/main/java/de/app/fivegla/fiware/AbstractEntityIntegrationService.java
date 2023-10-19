package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.CustomHeader;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.api.Validatable;
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
public abstract class AbstractEntityIntegrationService<T extends Validatable> extends AbstractIntegrationService<T> {

    public AbstractEntityIntegrationService(String contextBrokerUrl, String tenant) {
        super(contextBrokerUrl, tenant);
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
                .uri(URI.create(contextBrokerUrlForCommands() + "/op/update" + "?options=keyValues"))
                .header("Content-Type", "application/json")
                .header(CustomHeader.FIWARE_SERVICE, getTenant())
                .POST(HttpRequest.BodyPublishers.ofString(toJson(updateOrCreateEntityRequest))).build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                log.error("Could not create entity. Response: " + response.body());
                log.debug("Request: " + toJson(updateOrCreateEntityRequest));
                log.debug("Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not create entity, there was an error from FIWARE.");
            } else {
                log.info("Device created/updated successfully.");
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not create/update entity", e);
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
                .header(CustomHeader.FIWARE_SERVICE, getTenant())
                .uri(URI.create(contextBrokerUrlForCommands() + "/entities/" + id))
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
            throw new FiwareIntegrationLayerException("Could not check if device exists.", e);
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
                .header(CustomHeader.FIWARE_SERVICE, getTenant())
                .uri(URI.create(contextBrokerUrlForCommands() + "/entities/" + id + "?options=keyValues"))
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
                return Optional.of(toObject(response.body()));
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not read the device.", e);
        }
    }

}
