package de.app.fivegla.fiware.model;

import lombok.Getter;

/**
 * Device category values.
 */
public enum DeviceCategoryValues {

    SoilScoutSensor("soilScoutSensor");

    @Getter
    private final String key;

    DeviceCategoryValues(String key) {
        this.key = key;
    }
}
