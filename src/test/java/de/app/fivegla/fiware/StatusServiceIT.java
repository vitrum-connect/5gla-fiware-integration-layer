package de.app.fivegla.fiware;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StatusServiceIT {

    @Test
    void givenRunningFiwareInstanceWhenFetchingTheVersionThenTheVersionIsReturned() {
        var version = new StatusService("http://localhost:1026").getVersion();
        Assertions.assertNotNull(version);
        Assertions.assertNotNull(version.getVersion());
    }

}
