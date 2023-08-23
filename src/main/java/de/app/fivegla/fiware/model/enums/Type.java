package de.app.fivegla.fiware.model.enums;

import lombok.Getter;

/**
 * Device category values.
 */
@Getter
public enum Type {

    Device("Device"),
    Text("Text"),
    Point("Point"),
    DeviceMeasurement("DeviceMeasurement"),
    DroneDeviceMeasurement("DroneDeviceMeasurement");

    private final String key;

    Type(String key) {
        this.key = key;
    }
}
