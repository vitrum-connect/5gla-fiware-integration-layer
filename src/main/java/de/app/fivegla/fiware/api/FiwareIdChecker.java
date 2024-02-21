package de.app.fivegla.fiware.api;

import lombok.extern.slf4j.Slf4j;

/**
 * This class provides methods for generating FIWARE IDs.
 * FIWARE IDs are used to uniquely identify entities in the FIWARE ecosystem.
 */
@Slf4j
public final class FiwareIdChecker {

    public static final int MAX_ID_LENGTH = 62;

    private FiwareIdChecker() {
        // private constructor to prevent instantiation
    }

    /**
     * Checks if the given ID is valid.
     *
     * @param id the ID to be checked
     * @throws FiwareIntegrationLayerException if the ID is too long
     */
    public static void check(String id) {
        if (id.length() > MAX_ID_LENGTH) {
            log.error("The id is too long. Please choose a shorter prefix.");
            log.debug("Checked ID: " + id);
            throw new FiwareIntegrationLayerException("The generated id is too long. Please choose a shorter prefix.");
        }
    }

}
