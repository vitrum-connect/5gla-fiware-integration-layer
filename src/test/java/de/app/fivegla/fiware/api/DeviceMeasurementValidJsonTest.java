package de.app.fivegla.fiware.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.internal.DateTimeAttribute;
import de.app.fivegla.fiware.model.internal.NumberAttribute;
import de.app.fivegla.fiware.model.internal.TextAttribute;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

class DeviceMeasurementValidJsonTest {

    @Test
    void givenDeviceMeasurement_WhenCreatingJson_ThenTheResultShouldBeValidJson() {
        var deviceMeasurementAsJson = new DeviceMeasurement(
                UUID.randomUUID().toString(),
                "SENTEK_SENSOR",
                new TextAttribute("some - name"),
                new NumberAttribute(2.0),
                new DateTimeAttribute(Instant.now()),
                new TextAttribute("http://www.example.com"),
                4.0,
                8.0
        ).asJson();
        var objectMapper = new ObjectMapper();
        Assertions.assertDoesNotThrow(() -> objectMapper.readTree(deviceMeasurementAsJson));
    }

    @Test
    void givenDeviceMeasurement_WhenCreatingJson_ThenTheStructureShouldMatch() {
        String expectedStructure = """
                 {
                   "id": "b30a9d8d-66b3-4b43-b9ef-84e84573a2f1",
                   "type": "SENTEK_SENSOR",
                   "name": {
                     "type": "Text",
                     "value": "some-name"
                   },
                   "controlledProperty": {
                     "type": "Number",
                     "value": 2.0
                   },
                   "externalDataReference": {
                     "type": "Text",
                     "value": "http://www.example.com"
                   },
                   "dateCreated": {
                     "type": "DateTime",
                     "value": "2024-03-02T13:03:48.175Z"
                   },
                   "location": {
                     "type": "geo:json",
                     "value": {
                       "type": "Point",
                       "coordinates": [
                         8.0,
                         4.0
                       ]
                     }
                   }
                 }
                """;
        var deviceMeasurementAsJson = new DeviceMeasurement(
                "b30a9d8d-66b3-4b43-b9ef-84e84573a2f1",
                "SENTEK_SENSOR",
                new TextAttribute("some - name"),
                new NumberAttribute(2.0),
                new DateTimeAttribute(Instant.from(CustomDateFormatter.parse("2024-03-02T13:03:48.175Z"))),
                new TextAttribute("http://www.example.com"),
                4.0,
                8.0
        ).asJson();
        // Remove all whitespaces and newlines
        expectedStructure = expectedStructure.replaceAll("\\s", "");
        deviceMeasurementAsJson = deviceMeasurementAsJson.replaceAll("\\s", "");
        Assertions.assertEquals(expectedStructure, deviceMeasurementAsJson);
    }

}
