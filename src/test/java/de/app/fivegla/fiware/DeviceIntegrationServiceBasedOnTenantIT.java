package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.enums.DeviceCategoryValues;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class DeviceIntegrationServiceBasedOnTenantIT extends AbstractIT {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var fiwareIntegrationServiceForFoo = new DeviceIntegrationService(contextBrokerUrl, "foo");
        var fiwareIntegrationServiceForBar = new DeviceIntegrationService(contextBrokerUrl, "bar");
        var manufacturerSpecificIdForFoo = UUID.randomUUID().toString();
        var manufacturerSpecificIdForBar = UUID.randomUUID().toString();

        var deviceForFoo = Device.builder()
                .id("integration-test:" + manufacturerSpecificIdForFoo)
                .manufacturerSpecificId(manufacturerSpecificIdForFoo)
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(2.0, 4.0))
                        .build())
                .build();
        var deviceForBar = Device.builder()
                .id("integration-test:" + manufacturerSpecificIdForBar)
                .manufacturerSpecificId(manufacturerSpecificIdForBar)
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.Farm21Sensor.getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(2.0, 4.0))
                        .build())
                .build();

        fiwareIntegrationServiceForFoo.persist(deviceForFoo);
        fiwareIntegrationServiceForBar.persist(deviceForBar);

        Assertions.assertTrue(fiwareIntegrationServiceForFoo.exists(deviceForFoo.getId()));
        Assertions.assertFalse(fiwareIntegrationServiceForFoo.exists(deviceForBar.getId()));

        Assertions.assertTrue(fiwareIntegrationServiceForBar.exists(deviceForBar.getId()));
        Assertions.assertFalse(fiwareIntegrationServiceForBar.exists(deviceForFoo.getId()));
    }

}