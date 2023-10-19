package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.enums.Type;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SubscriptionServiceIT extends AbstractIT {

    @AfterEach
    void tearDown() {
        var fiwareIntegrationService = new SubscriptionService(contextBrokerUrl, tenant, notificationUrls);
        fiwareIntegrationService.removeAll(Type.Device);
    }

    @Test
    void givenValidSubscriptionWhenSendingSubscriptionToFiwareThereShouldBeNoError() {
        var fiwareIntegrationService = new SubscriptionService(contextBrokerUrl, tenant, notificationUrls);
        fiwareIntegrationService.subscribe(Type.Device);
        var subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
    }

    @Test
    void givenExistingSubscriptionWhenRemovingAllSubscriptionsTheNumberOfExistingSubscriptionsShouldBeNull() {
        var fiwareIntegrationService = new SubscriptionService(contextBrokerUrl, tenant, notificationUrls);
        fiwareIntegrationService.subscribe(Type.Device);
        var subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
        Assertions.assertEquals(1, subscriptions.size());
        fiwareIntegrationService.removeAll(Type.Device);
        subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
        Assertions.assertEquals(0, subscriptions.size());
    }

    @Test
    void givenExistingSubscriptionWhenSubscribingAndResettingTheNumberOfExistingSubscriptionsShouldStayTheSame() {
        var fiwareIntegrationService = new SubscriptionService(contextBrokerUrl, tenant, notificationUrls);
        fiwareIntegrationService.subscribe(Type.Device);
        var subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
        Assertions.assertEquals(1, subscriptions.size());
        fiwareIntegrationService.subscribeAndReset(Type.Device);
        subscriptions = fiwareIntegrationService.findAll(Type.Device);
        Assertions.assertNotNull(subscriptions);
        Assertions.assertEquals(1, subscriptions.size());
    }

}