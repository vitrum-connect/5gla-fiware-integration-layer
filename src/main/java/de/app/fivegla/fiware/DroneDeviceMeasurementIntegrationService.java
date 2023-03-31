package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.DroneDeviceMeasurement;
import lombok.extern.slf4j.Slf4j;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Slf4j
public class DroneDeviceMeasurementIntegrationService extends AbstractIntegrationService<DroneDeviceMeasurement> {

    public DroneDeviceMeasurementIntegrationService(String contextBrokerUrl) {
        super(contextBrokerUrl);
    }

    @Override
    DroneDeviceMeasurement parseResponse(String responseBody) {
        return GSON.fromJson(responseBody, DroneDeviceMeasurement.class);
    }
}
