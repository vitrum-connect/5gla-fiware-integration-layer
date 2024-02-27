package de.app.fivegla.fiware.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests the FiwareIdChecker class.
 * It ensures that the check method functions as expected, throwing an exception when necessary.
 */
public final class FiwareCheckerTest {

    /**
     * Test for the 'check' method in the FiwareIdChecker class when the ID length is valid.
     */
    @Test
    public void check_ValidId_DoesNotThrow() {
        String id = "validID";

        // No exception should be thrown
        assertDoesNotThrow(() -> FiwareChecker.checkId(id));
    }

    /**
     * Test for the 'check' method in the FiwareIdChecker class when the ID length is too long.
     */
    @Test
    public void check_InvalidId_FiwareIntegrationLayerExceptionIsThrown() {
        // create an id longer than MAX_ID_LENGTH
        String longId = "A".repeat(FiwareChecker.MAX_ID_LENGTH + 1);
        assertThrows(FiwareIntegrationLayerException.class, () -> FiwareChecker.checkId(longId));
    }

    /**
     * Test for the 'check' method in the FiwareIdChecker class when the ID length is valid.
     */
    @Test
    public void check_ValidType_DoesNotThrow() {
        String type = "validType";

        // No exception should be thrown
        assertDoesNotThrow(() -> FiwareChecker.checkType(type));
    }

    /**
     * Test for the 'check' method in the FiwareTypeChecker class when the ID length is too long.
     */
    @Test
    public void check_InvalidType_FiwareIntegrationLayerExceptionIsThrown() {
        // create an id longer than MAX_ID_LENGTH
        String longType = "A".repeat(FiwareChecker.MAX_TYPE_LENGTH + 1);
        assertThrows(FiwareIntegrationLayerException.class, () -> FiwareChecker.checkType(longType));
    }
}