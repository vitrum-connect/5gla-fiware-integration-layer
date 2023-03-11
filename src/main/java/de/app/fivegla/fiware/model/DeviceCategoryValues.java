package de.app.fivegla.fiware.model;

import lombok.Getter;

/**
 * Device category values.
 */
public enum DeviceCategoryValues {

    SoilScoutSensor("soilScoutSensor"),
    Farm21Sensor("farm21Sensor");

    @Getter
    private final String key;

    DeviceCategoryValues(String key) {
        this.key = key;
    }
}
