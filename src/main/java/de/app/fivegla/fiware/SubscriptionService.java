package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.*;
import de.app.fivegla.fiware.model.enums.Type;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;


/**
 * SubscriptionService is a class that handles subscribing to a specific type in a context broker.
 * It extends the AbstractIntegrationService class and provides methods for creating a subscription and handling responses.
 *
 * @see AbstractIntegrationService
 */
@Slf4j
public class SubscriptionService extends AbstractIntegrationService<Subscription> {
    private final String notificationUrl;

    public SubscriptionService(String contextBrokerUrl, String notificationUrl) {
        super(contextBrokerUrl);
        this.notificationUrl = notificationUrl;
    }

    public void subscribe(Type type) {
        var httpClient = HttpClient.newHttpClient();
        var subscription = createSubscriptionForType(type);
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/subscriptions"))
                .header("Content-Type", "application/json")
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

    public List<Subscription> findAll(Type type) {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(contextBrokerUrl + "/subscriptions"))
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
                        .anyMatch(entity -> entity.getType().equals(type.getKey()))).toList();
            }
        } catch (Exception e) {
            throw new FiwareIntegrationLayerException("Could not find subscription", e);
        }
    }

    private Subscription createSubscriptionForType(Type type) {
        return Subscription.builder()
                .description("Subscription for " + type.getKey() + " type")
                .subject(Subject.builder()
                        .entities(List.of(Entity.builder()
                                .idPattern(".*")
                                .type(type.getKey())
                                .build()))
                        .build())
                .notification(Notification.builder()
                        .http(Http.builder()
                                .url(notificationUrl)
                                .build())
                        .build())
                .build();
    }
}
