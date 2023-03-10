package de.app.fivegla.fiware;

import java.io.IOException;
import java.util.Properties;

/**
 * Integration service for FIWARE to send requests to the context broker.
 */
public class FiwareIntegrationService {

    private final String contextBrokerUrl;
    private Properties properties;

    public FiwareIntegrationService(String contextBrokerUrl) {
        this.contextBrokerUrl = contextBrokerUrl;
        initProperties();
    }

    private void initProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/package.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Could not load package.properties", e);
        }
    }

    public String getCurrentVersion() {
        return properties.getProperty(PropertyKeys.APP_VERSION);
    }

}
