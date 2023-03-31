package de.app.fivegla.fiware.api.enums;

import lombok.Getter;

/**
 * Device category values.
 */
public enum Types {

    Device("Device"),
    Text("Text"),
    Point("Point"),
    DeviceMeasurement("DeviceMeasurement"),
    DroneDeviceMeasurement("DroneDeviceMeasurement");

    @Getter
    private final String key;

    Types(String key) {
        this.key = key;
    }
}
