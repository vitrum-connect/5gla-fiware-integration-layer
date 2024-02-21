package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class DeviceMeasurementIntegrationServiceIT extends AbstractIT {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceMeasurement.getId()));
    }

    @Test
    void givenAlreadyExistingDeviceWhenCreatingNewDevicesTheServiceShouldNotThrowAnException() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceId));
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceId));
    }

    @Test
    void givenAlreadyExistingDeviceWhenUpdatingTheDeviceTheServiceShouldUpdateTheValuesForTheDevice() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceId));
        var persistedDeviceMeasurement = deviceMeasurementIntegrationService.read(deviceId);
        Assertions.assertTrue(persistedDeviceMeasurement.isPresent());
        Assertions.assertEquals(2.4, persistedDeviceMeasurement.get().getNumValue());

        deviceMeasurement.setNumValue(8.16);
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceId));
        persistedDeviceMeasurement = deviceMeasurementIntegrationService.read(deviceId);
        Assertions.assertTrue(persistedDeviceMeasurement.isPresent());
        Assertions.assertEquals(8.16, persistedDeviceMeasurement.get().getNumValue());
    }

    @Test
    void givenExistingDeviceWhenCheckingIfTheDeviceDoesExistTheServiceShouldReturnTrue() {
        var deviceMeasurementIntegrationService = new DeviceMeasurementIntegrationService(contextBrokerUrl, tenant);
        var manufacturerSpecificId = "";
        var deviceId = "integration-test:" + manufacturerSpecificId;
        var location = Location.builder().coordinates(List.of(1.0, 2.0)).build();
        var deviceMeasurement = DeviceMeasurement.builder().id(deviceId).numValue(2.4).location(location).build();
        deviceMeasurementIntegrationService.persist(deviceMeasurement);
        Assertions.assertTrue(deviceMeasurementIntegrationService.exists(deviceId));
        Assertions.assertFalse(deviceMeasurementIntegrationService.exists("integration-test:does-not-exist"));
    }

}