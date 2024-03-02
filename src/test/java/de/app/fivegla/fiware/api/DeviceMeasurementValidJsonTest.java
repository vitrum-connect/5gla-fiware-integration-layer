package de.app.fivegla.fiware.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.fiware.model.builder.DeviceMeasurementBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

class DevxiceMeasurementValidJsonTest {

    @Test
    void givenDeviceMeasurement_WhenCreatingJson_ThenTheResultShouldBeValidJson() throws Exception {
        var deviceMeasurementAsJson = new DeviceMeasurementBuilder()
                .withId(UUID.randomUUID().toString())
                .withType(FiwareType.TEXT.name())
                .withMeasurement("value", FiwareType.TEXT, 2.0, Instant.now(), 4.0, 8.0)
                .build()
                .asJson();
        var objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(deviceMeasurementAsJson);
        Assertions.assertEquals(FiwareType.TEXT.getKey(), jsonNode.get("type").asText());
        Assertions.assertEquals(2.0, jsonNode.get("value").asDouble());
    }

    @Test
    void givenDeviceMeasurementWithExternalDataReference_WhenCreatingJson_ThenTheResultShouldBeValidJson() throws Exception {
        var deviceMeasurementAsJson = new DeviceMeasurementBuilder()
                .withId(UUID.randomUUID().toString())
                .withType(FiwareType.TEXT.name())
                .withMeasurement("value", FiwareType.TEXT, 2.0, Instant.now(), 4.0, 8.0)
                .withExternalDataReference("http://example.com")
                .build()
                .asJson();
        var objectMapper = new ObjectMapper();
        var jsonNode = objectMapper.readTree(deviceMeasurementAsJson);
        Assertions.assertEquals(FiwareType.TEXT.getKey(), jsonNode.get("type").asText());
        Assertions.assertEquals(2.0, jsonNode.get("value").asDouble());
    }

}
