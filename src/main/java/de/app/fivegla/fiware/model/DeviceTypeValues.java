package de.app.fivegla.fiware.model;

import lombok.Getter;

/**
 * Device category values.
 */
public enum DeviceTypeValues {

    Device("Device");

    @Getter
    private final String key;

    DeviceTypeValues(String key) {
        this.key = key;
    }
}
