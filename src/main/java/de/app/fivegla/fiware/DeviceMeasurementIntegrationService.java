package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.DeviceMeasurement;
import lombok.extern.java.Log;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Log
public class DeviceMeasurementIntegrationService extends AbstractIntegrationService<DeviceMeasurement> {

    public DeviceMeasurementIntegrationService(String contextBrokerUrl) {
        super(contextBrokerUrl);
    }

    @Override
    DeviceMeasurement parseResponse(String responseBody) {
        return GSON.fromJson(responseBody, DeviceMeasurement.class);
    }
}
