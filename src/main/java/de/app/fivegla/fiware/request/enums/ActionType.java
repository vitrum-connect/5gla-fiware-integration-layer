package de.app.fivegla.fiware.request.enums;

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
