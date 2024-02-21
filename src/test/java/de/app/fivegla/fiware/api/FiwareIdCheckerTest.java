package de.app.fivegla.fiware.api;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
  
/**
 * This class tests the FiwareIdChecker class.
 * It ensures that the check method functions as expected, throwing an exception when necessary.
 */
public final class FiwareIdCheckerTest {
    
    /**
     * Test for the 'check' method in the FiwareIdChecker class when the ID length is valid.
     */
    @Test
    public void check_ValidId_DoesNotThrow() {
        String id = "validID";
      
        // No exception should be thrown
        assertDoesNotThrow(() -> FiwareIdChecker.check(id));
    }

    /**
     * Test for the 'check' method in the FiwareIdChecker class when the ID length is too long.
     */
    @Test
    public void check_InvalidId_FiwareIntegrationLayerExceptionIsThrown() {
        // create an id longer than MAX_ID_LENGTH
        String longId = "A".repeat(FiwareIdChecker.MAX_ID_LENGTH + 1);
        assertThrows(FiwareIntegrationLayerException.class, () -> FiwareIdChecker.check(longId));
    }
}