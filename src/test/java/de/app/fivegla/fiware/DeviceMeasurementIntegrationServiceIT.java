package de.app.fivegla.fiware;

import de.app.fivegla.fiware.api.enums.DeviceCategoryValues;
import de.app.fivegla.fiware.model.Device;
import de.app.fivegla.fiware.model.DeviceCategory;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class DeviceMeasurementIntegrationServiceIT {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService("http://localhost:1026/v2");
        var device = Device.builder().id("integration-test:" + UUID.randomUUID()).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var deviceMeasurement = DeviceMeasurement.builder().id("integration-test:" + UUID.randomUUID()).refDevice(device.getId()).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceMeasurement.getId()));
    }

    @Test
    void givenAlreadyExistingDeviceWhenCreatingNewDevicesTheServiceShouldNotThrowAnException() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService("http://localhost:1026/v2");
        String deviceId = "integration-test:" + UUID.randomUUID();
        var device = Device.builder().id(deviceId).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        String deviceMeasurementId = "integration-test:" + UUID.randomUUID();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceMeasurementId).refDevice(device.getId()).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceMeasurementId));
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceMeasurementId));
    }

    @Test
    void givenAlreadyExistingDeviceWhenUpdatingTheDeviceTheServiceShouldUpdateTheValuesForTheDevice() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService("http://localhost:1026/v2");
        String deviceId = "integration-test:" + UUID.randomUUID();
        var device = Device.builder().id(deviceId).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        String deviceMeasurementId = "integration-test:" + UUID.randomUUID();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceMeasurementId).refDevice(device.getId()).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceMeasurementId));
        var persistedDeviceMeasurement = deviceMeasurementIntegrationService.read(deviceMeasurementId);
        Assertions.assertTrue(persistedDeviceMeasurement.isPresent());
        Assertions.assertEquals(deviceId, persistedDeviceMeasurement.get().getRefDevice());
        Assertions.assertEquals(2.4, persistedDeviceMeasurement.get().getNumValue());

        deviceMeasurement.setNumValue(8.16);
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceMeasurementId));
        persistedDeviceMeasurement = deviceMeasurementIntegrationService.read(deviceMeasurementId);
        Assertions.assertTrue(persistedDeviceMeasurement.isPresent());
        Assertions.assertEquals(deviceId, persistedDeviceMeasurement.get().getRefDevice());
        Assertions.assertEquals(8.16, persistedDeviceMeasurement.get().getNumValue());
    }

    @Test
    void givenExistingDeviceWhenCheckingIfTheDeviceDoesExistTheServiceShouldReturnTrue() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService("http://localhost:1026/v2");
        String deviceId = "integration-test:" + UUID.randomUUID();
        var device = Device.builder().id(deviceId).deviceCategory(DeviceCategory.builder().value(List.of(DeviceCategoryValues.SoilScoutSensor.getKey())).build()).build();
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        String deviceMeasurementId = "integration-test:" + UUID.randomUUID();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceMeasurementId).refDevice(device.getId()).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceMeasurementId));
        Assertions.assertFalse(deviceMeasurementIntegrationService.exists("integration-test:does-not-exist"));
    }

}