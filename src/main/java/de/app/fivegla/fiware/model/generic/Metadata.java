package de.app.fivegla.fiware.model.generic;

import lombok.Getter;
import lombok.Setter;

/**
 * The Metadata class represents a metadata attribute with a name, type, and value.
 * Each instance of Metadata is associated with an Attribute object.
 */
@Getter
@Setter
public class Metadata {
    /**
     * The name of the metadata attribute.
     * <p>
     * This variable represents the name of the metadata attribute. It is a private instance variable
     * in the Attribute class. The name is stored as a String.
     */
    private String name;

    /**
     * The type of the metadata attribute.
     * <p>
     * This variable represents the type of the attribute. It is a private instance variable
     * in the Attribute class. The type is stored as a String.
     */
    private String type;

    /**
     * The value of the metadata attribute.
     * <p>
     * This variable represents the value of the metadata attribute. It is a private instance variable
     * in the Attribute class. The value is stored as a String.
     */
    private String value;

    public String asJson() {
        return "{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"value\":\"" + value + "\"}";
    }
}
