package de.app.fivegla.fiware;

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
        fiwareIntegrationService.persist(device);
    }

    @Test
    void givenAlreadyExistingDeviceWhenCreatingNewDevicesTheServiceShouldNotThrowAnException() {
        var fiwareIntegrationService = new FiwareIntegrationService("http://localhost:1026/v2");
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder().id(id).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
    }

    @Test
    void givenAlreadyExistingDeviceWhenUpdatingTheDeviceTheServiceShouldUpdateTheValuesForTheDevice() {
        var fiwareIntegrationService = new FiwareIntegrationService("http://localhost:1026/v2");
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder().id(id).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        var persistedDevice = fiwareIntegrationService.readDevice(id);
        Assertions.assertTrue(persistedDevice.isPresent());
        Assertions.assertEquals(DeviceCategoryValues.SoilScoutSensor.getKey(), persistedDevice.get().getDeviceCategory().getValue().get(0));

        device.setDeviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.Farm21Sensor.getKey())).build());
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        persistedDevice = fiwareIntegrationService.readDevice(id);
        Assertions.assertTrue(persistedDevice.isPresent());
        Assertions.assertEquals(DeviceCategoryValues.Farm21Sensor.getKey(), persistedDevice.get().getDeviceCategory().getValue().get(0));
    }

    @Test
    void givenExistingDeviceWhenCheckingIfTheDeviceDoesExistTheServiceShouldReturnTrue() {
        var fiwareIntegrationService = new FiwareIntegrationService("http://localhost:1026/v2");
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder().id(id).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        Assertions.assertFalse(fiwareIntegrationService.exists("integration-test:does-not-exist"));
    }

}