package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceCategoryValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DeviceJsonFormatTest {

    @Test
    void givenValidDeviceTheJsonShouldMatchTheExpectedFormat() {
        var expectedJson = """
                {
                  "id": "example:device-9845A",
                  "type": "Device",
                  "deviceCategory": {
                    "type": "Text",
                    "value": [
                      "soilScoutSensor"
                    ]
                  }
                }""";
        var actualJson = FiwareIntegrationService.GSON.toJson(Device.builder().id("example:device-9845A").deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build());
        Assertions.assertEquals(expectedJson, actualJson);
    }

}
