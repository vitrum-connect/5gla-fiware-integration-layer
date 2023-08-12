package de.app.fivegla.fiware.api.enums;

import lombok.Getter;

/**
 * Device category values.
 */
@Getter
public enum DeviceCategoryValues {

    SoilScoutSensor("soilScoutSensor"),
    Farm21Sensor("farm21Sensor"),
    MicaSenseDrone("micaSenseDrone");

    private final String key;

    DeviceCategoryValues(String key) {
        this.key = key;
    }
}
