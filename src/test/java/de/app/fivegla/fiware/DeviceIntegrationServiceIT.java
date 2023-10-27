package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import de.app.fivegla.fiware.api.enums.DeviceCategoryValues;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class DeviceIntegrationServiceIT extends AbstractIT {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var fiwareIntegrationService = new DeviceIntegrationService(contextBrokerUrl, tenant);
        var device = Device.builder()
                .id("integration-test:" + UUID.randomUUID())
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(2.0, 4.0))
                        .build())
                .build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(device.getId()));
    }

    @Test
    void givenAlreadyExistingDeviceWhenCreatingNewDevicesTheServiceShouldNotThrowAnException() {
        var fiwareIntegrationService = new DeviceIntegrationService(contextBrokerUrl, tenant);
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder()
                .id(id)
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(2.0, 4.0))
                        .build())
                .build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
    }

    @Test
    void givenAlreadyExistingDeviceWhenUpdatingTheDeviceTheServiceShouldUpdateTheValuesForTheDevice() {
        var fiwareIntegrationService = new DeviceIntegrationService(contextBrokerUrl, tenant);
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder()
                .id(id)
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(2.0, 4.0))
                        .build())
                .build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        var persistedDevice = fiwareIntegrationService.read(id);
        Assertions.assertTrue(persistedDevice.isPresent());
        Assertions.assertEquals(DeviceCategoryValues.SoilScoutSensor.getKey(), persistedDevice.get().getDeviceCategory().getValue().get(0));

        device.setDeviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.Farm21Sensor.getKey())).build());
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        persistedDevice = fiwareIntegrationService.read(id);
        Assertions.assertTrue(persistedDevice.isPresent());
        Assertions.assertEquals(DeviceCategoryValues.Farm21Sensor.getKey(), persistedDevice.get().getDeviceCategory().getValue().get(0));
    }

    @Test
    void givenExistingDeviceWhenCheckingIfTheDeviceDoesExistTheServiceShouldReturnTrue() {
        var fiwareIntegrationService = new DeviceIntegrationService(contextBrokerUrl, tenant);
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder()
                .id(id)
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(2.0, 4.0))
                        .build())
                .build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        Assertions.assertFalse(fiwareIntegrationService.exists("integration-test:does-not-exist"));
    }

    @Test
    void givenExistingDeviceWhenDeletingIfTheDeviceDoesExistTheServiceShouldReturnTrue() {
        var fiwareIntegrationService = new DeviceIntegrationService(contextBrokerUrl, tenant);
        var id = "integration-test:" + UUID.randomUUID();
        var device = Device.builder()
                .id(id)
                .deviceCategory(DeviceCategory.builder()
                        .value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey()))
                        .build())
                .location(Location.builder()
                        .coordinates(List.of(2.0, 4.0))
                        .build())
                .build();
        fiwareIntegrationService.persist(device);
        Assertions.assertTrue(fiwareIntegrationService.exists(id));
        Assertions.assertTrue(fiwareIntegrationService.delete(id));
        Assertions.assertFalse(fiwareIntegrationService.exists(id));
        Assertions.assertThrows(FiwareIntegrationLayerException.class, () -> fiwareIntegrationService.delete(id));
    }


}