package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.enums.DeviceCategoryValues;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class DeviceIntegrationServiceBasedOnTenantIT {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var fiwareIntegrationServiceForFoo = new DeviceIntegrationService("http://localhost:1026", "foo");
        var fiwareIntegrationServiceForBar = new DeviceIntegrationService("http://localhost:1026", "bar");

        var deviceForFoo = Device.builder().id("integration-test:" + UUID.randomUUID()).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        var deviceForBar = Device.builder().id("integration-test:" + UUID.randomUUID()).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.Farm21Sensor.getKey())).build()).build();

        fiwareIntegrationServiceForFoo.persist(deviceForFoo);
        fiwareIntegrationServiceForBar.persist(deviceForBar);

        Assertions.assertTrue(fiwareIntegrationServiceForFoo.exists(deviceForFoo.getId()));
        Assertions.assertFalse(fiwareIntegrationServiceForFoo.exists(deviceForBar.getId()));

        Assertions.assertTrue(fiwareIntegrationServiceForBar.exists(deviceForBar.getId()));
        Assertions.assertFalse(fiwareIntegrationServiceForBar.exists(deviceForFoo.getId()));
    }

}