package de.app.fivegla.fiware.api;

import lombok.Getter;

/**
 * This enumeration represents the types supported by Fiware.
 * <p>
 * Each FiwareType has a unique key which represents its value. The supported types are:
 * - GEO_POINT: Represents a geographical point.
 * - DATE_TIME: Represents a date and time.
 */
@Getter
public enum FiwareTypes {
    GEO_POINT("geo:point"),
    DATE_TIME("DateTime"),
    TEXT("Text");

    private final String key;

    FiwareTypes(String key) {
        this.key = key;
    }
}
