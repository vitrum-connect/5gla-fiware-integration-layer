package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.Device;
import lombok.extern.java.Log;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Log
public class DeviceIntegrationService extends AbstractIntegrationService<Device> {

    public DeviceIntegrationService(String contextBrokerUrl) {
        super(contextBrokerUrl);
    }

    @Override
    Device parseResponse(String responseBody) {
        return GSON.fromJson(responseBody, Device.class);
    }

}
