package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.DeviceMeasurement;
import lombok.extern.slf4j.Slf4j;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Slf4j
public class DeviceMeasurementIntegrationService extends AbstractEntityIntegrationService<DeviceMeasurement> {

    public DeviceMeasurementIntegrationService(String contextBrokerUrl) {
        super(contextBrokerUrl);
    }

}
