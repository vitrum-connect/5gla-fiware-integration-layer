package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceCategoryValues;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class FiwareIntegrationServiceIntegrationTest {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var fiwareIntegrationService = new FiwareIntegrationService("http://localhost:1026/v2");
        var device = Device.builder().id("integration-test:" + UUID.randomUUID()).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        fiwareIntegrationService.createDevice(device);
    }

    @Test
    void givenAlreadyExistingDeviceWhenCreatingNewDevicesTheServiceShouldThrowAnException() {
        var fiwareIntegrationService = new FiwareIntegrationService("http://localhost:1026/v2");
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder().id(id).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        fiwareIntegrationService.createDevice(device);
        Assertions.assertThrows(FiwareIntegrationLayerException.class, () -> fiwareIntegrationService.createDevice(device));
    }

}