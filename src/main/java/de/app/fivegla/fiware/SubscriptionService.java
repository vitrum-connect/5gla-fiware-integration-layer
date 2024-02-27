package de.app.fivegla.fiware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.fiware.api.CustomHeader;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.cygnus.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * SubscriptionService is a class that handles subscribing to a specific type in a context broker.
 * It extends the AbstractIntegrationService class and provides methods for creating a subscription and handling responses.
 *
 * @see AbstractIntegrationService
 */
@Slf4j
@SuppressWarnings("unused")
public class SubscriptionService extends AbstractIntegrationService {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final List<String> notificationUrls;

    public SubscriptionService(String contextBrokerUrl, String tenant, List<String> notificationUrls) {
        super(contextBrokerUrl, tenant);
        this.notificationUrls = notificationUrls;
    }

    public void subscribeAndReset(String type) {
        removeAll(type);
        subscribe(type);
    }

    public void subscribe(String type) {
        var httpClient = HttpClient.newHttpClient();
        var subscriptions = createSubscriptionForType(type);
        for (var subscription : subscriptions) {
            var httpRequest = HttpRequest.newBuilder()
                    .uri(URI.create(contextBrokerUrlForCommands() + "/subscriptions"))
                    .header("Content-Type", "application/json")
                    .header(CustomHeader.FIWARE_SERVICE, getTenant())
                    .POST(HttpRequest.BodyPublishers.ofString(toJson(subscription))).build();
            try {
                var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 201) {
                    log.error("Could not create subscription. Response: " + response.body());
                    log.debug("Request: " + toJson(subscription));
                    log.debug("Response: " + response.body());
                    throw new FiwareIntegrationLayerException("Could not create subscription, there was an error from FIWARE.");
                } else {
                    log.info("Subscription created/updated successfully.");
                }
            } catch (Exception e) {
                throw new FiwareIntegrationLayerException("Could not create/update subscription", e);
            }
        }
    }

    public void removeAll(String type) {
        findAll(type).forEach(this::removeSubscription);
    }

    private void removeSubscription(Subscription subscription) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .header(CustomHeader.FIWARE_SERVICE, getTenant())
                .uri(URI.create(contextBrokerUrlForCommands() + "/subscriptions/" + subscription.getId()))
                .DELETE().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                log.error("Could not remove subscription. Response: " + response.body());
                log.debug("Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not remove subscription, there was an error from FIWARE.");
            } else {
                log.info("Subscription removed successfully.");
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not remove subscription", e);
        }
    }

    public List<Subscription> findAll(String type) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .header(CustomHeader.FIWARE_SERVICE, getTenant())
                .uri(URI.create(contextBrokerUrlForCommands() + "/subscriptions"))
                .GET().build();
        try {
            var response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                log.error("Could not find subscriptions. Response: " + response.body());
                log.debug("Response: " + response.body());
                throw new FiwareIntegrationLayerException("Could not find subscription, there was an error from FIWARE.");
            } else {
                log.info("Subscription found successfully.");
                return toListOfObjects(response.body()).stream().filter(subscription -> subscription.getSubject().getEntities()
                        .stream()
                        .anyMatch(entity -> entity.getType().equals(type))).toList();
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not find subscription", e);
        }
    }

    /**
     * Converts the current object to a JSON string representation.
     *
     * @param object the object to convert
     * @return a JSON string representing the current object
     * @throws FiwareIntegrationLayerException if an error occurs while transforming the object to JSON
     */
    private String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FiwareIntegrationLayerException("Could not transform object to JSON.", e);
        }
    }

    private Subscription toObject(String json) {
        try {
            var type = OBJECT_MAPPER.getTypeFactory()
                    .constructType(Subscription.class);
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new FiwareIntegrationLayerException("Could not transform JSON to object.", e);
        }
    }

    private List<Subscription> toListOfObjects(String json) {
        try {
            var type = OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, Subscription.class);
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new FiwareIntegrationLayerException("Could not transform JSON to object.", e);
        }
    }

    private List<Subscription> createSubscriptionForType(String type) {
        var subscriptions = new ArrayList<Subscription>();
        for (var notificationUrl : notificationUrls) {
            var subscription = Subscription.builder()
                    .description("Subscription for " + type + " type")
                    .subject(Subject.builder()
                            .entities(List.of(Entity.builder()
                                    .idPattern(".*")
                                    .type(type)
                                    .build()))
                            .build())
                    .notification(Notification.builder()
                            .http(Http.builder()
                                    .url(notificationUrl)
                                    .build())
                            .build())
                    .build();
            subscriptions.add(subscription);
        }
        return subscriptions;
    }
}
