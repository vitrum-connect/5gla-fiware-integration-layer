package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.enums.Type;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubscriptionServiceTest {

    @AfterEach
    void tearDown() {
        var fiwareIntegrationService = new SubscriptionService("http://localhost:1026/v2", "http://192.168.56.1:5055/notify");
        fiwareIntegrationService.removeAll(Type.Device);
    }

    @Test
    void givenValidSubscriptionWhenSendingSubscriptionToFiwareThereShouldBeNoError() {
        var fiwareIntegrationService = new SubscriptionService("http://localhost:1026/v2", "http://192.168.56.1:5055/notify");
        fiwareIntegrationService.subscribe(Type.Device);
        var subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
    }

    @Test
    void givenExistingSubscriptionWhenRemovingAllSubscriptionsTheNumberOfExistingSubscriptionsShouldBeNull() {
        var fiwareIntegrationService = new SubscriptionService("http://localhost:1026/v2", "http://192.168.56.1:5055/notify");
        fiwareIntegrationService.subscribe(Type.Device);
        var subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
        Assertions.assertEquals(1, subscriptions.size());
        fiwareIntegrationService.removeAll(Type.Device);
        subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
        Assertions.assertEquals(0, subscriptions.size());
    }

}