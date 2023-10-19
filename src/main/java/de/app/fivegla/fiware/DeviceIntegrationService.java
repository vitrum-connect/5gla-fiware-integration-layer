package de.app.fivegla.fiware;

import de.app.fivegla.fiware.model.Device;
import lombok.extern.slf4j.Slf4j;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
@Slf4j
public class DeviceIntegrationService extends AbstractEntityIntegrationService<Device> {

    public DeviceIntegrationService(String contextBrokerUrl, String tenant) {
        super(contextBrokerUrl, tenant);
    }

}
