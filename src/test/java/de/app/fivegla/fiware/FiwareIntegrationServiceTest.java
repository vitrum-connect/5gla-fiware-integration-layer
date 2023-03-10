package de.app.fivegla.fiware;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FiwareIntegrationServiceTest {

    @Test
    void givenExistingPackagePropertiesWhenFetchingTheVersionTheServiceShouldReturnTheCurrentVersion() {
        var fiwareIntegrationService = new FiwareIntegrationService("not used");
        String currentVersion = fiwareIntegrationService.getCurrentVersion();
        assertEquals("1.0.0", currentVersion);
    }

}