package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.DroneDeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class DroneDeviceMeasurementIntegrationServiceIT extends AbstractIT {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var deviceMeasurementIntegrationService = new DroneDeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        var droneDeviceMeasurement = DroneDeviceMeasurement.builder().id("integration-test:" + UUID.randomUUID()).deviceMeasurement(deviceMeasurement).channel("red").imagePath("http://localhost:8080/images/" + UUID.randomUUID()).build();
        deviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(droneDeviceMeasurement.getId()));
    }

    @Test
    void givenAlreadyExistingDeviceWhenCreatingNewDevicesTheServiceShouldNotThrowAnException() {
        var deviceMeasurementIntegrationService = new DroneDeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var droneDeviceMeasurementId = "integration-test:" + UUID.randomUUID();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        var droneDeviceMeasurement = DroneDeviceMeasurement.builder().id(droneDeviceMeasurementId).deviceMeasurement(deviceMeasurement).channel("red").imagePath("http://localhost:8080/images/" + UUID.randomUUID()).build();
        deviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(droneDeviceMeasurementId));
        deviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(droneDeviceMeasurementId));
    }

    @Test
    void givenAlreadyExistingDeviceWhenUpdatingTheDeviceTheServiceShouldUpdateTheValuesForTheDevice() {
        var deviceMeasurementIntegrationService = new DroneDeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var droneDeviceMeasurementId = "integration-test:" + UUID.randomUUID();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        var droneDeviceMeasurement = DroneDeviceMeasurement.builder().id(droneDeviceMeasurementId).deviceMeasurement(deviceMeasurement).channel("red").imagePath("http://localhost:8080/images/" + UUID.randomUUID()).build();
        deviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(droneDeviceMeasurementId));
        var persistedDeviceMeasurement = deviceMeasurementIntegrationService.read(droneDeviceMeasurementId);
        Assertions.assertTrue(persistedDeviceMeasurement.isPresent());

        deviceMeasurement.setNumValue(8.16);
        deviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(droneDeviceMeasurementId));
        persistedDeviceMeasurement = deviceMeasurementIntegrationService.read(droneDeviceMeasurementId);
        Assertions.assertTrue(persistedDeviceMeasurement.isPresent());
    }

    @Test
    void givenExistingDeviceWhenCheckingIfTheDeviceDoesExistTheServiceShouldReturnTrue() {
        var deviceMeasurementIntegrationService = new DroneDeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var droneDeviceMeasurementId = "integration-test:" + UUID.randomUUID();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        var droneDeviceMeasurement = DroneDeviceMeasurement.builder().id(droneDeviceMeasurementId).deviceMeasurement(deviceMeasurement).channel("red").imagePath("http://localhost:8080/images/" + UUID.randomUUID()).build();
        deviceMeasurementIntegrationService.persist(droneDeviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(droneDeviceMeasurementId));
        Assertions.assertFalse(deviceMeasurementIntegrationService.exists("integration-test:does-not-exist"));
    }

}