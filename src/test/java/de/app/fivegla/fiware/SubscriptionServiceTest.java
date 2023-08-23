package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.enums.Type;
import org.junit.jupiter.api.Test;

class SubscriptionServiceTest {

    @Test
    void givenValidSubscriptionWhenSendingSubscriptionToFiwareThereShouldBeNoError() {
        var fiwareIntegrationService = new SubscriptionService("http://localhost:1026/v2", "http://192.168.56.1:5055/notify");
        fiwareIntegrationService.subscribe(Type.Device);
        var subscriptions = fiwareIntegrationService.findAll(Type.Device);
    }

}