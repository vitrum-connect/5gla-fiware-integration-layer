package de.app.fivegla.fiware.api.enums;

import lombok.Getter;

/**
 * Action type.
 */
public enum ActionType {

    APPEND("append");

    @Getter
    private final String key;

    ActionType(String key) {
        this.key = key;
    }
}
