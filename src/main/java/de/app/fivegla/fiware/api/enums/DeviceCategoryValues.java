package de.app.fivegla.fiware.api.enums;

import lombok.Getter;

/**
 * Device category values.
 */
public enum DeviceCategoryValues {

    SoilScoutSensor("soilScoutSensor"),
    Farm21Sensor("farm21Sensor"),

    MicaSenseDrone("micaSenseDrone");

    @Getter
    private final String key;

    DeviceCategoryValues(String key) {
        this.key = key;
    }
}
