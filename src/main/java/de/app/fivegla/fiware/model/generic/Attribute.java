package de.app.fivegla.fiware.model.generic;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Attribute class represents an attribute with a name, type, and value.
 */
@Getter
@Setter
public class Attribute {

    /**
     * The name of an attribute.
     * <p>
     * This variable represents the name of an attribute. It is a private instance variable
     * in the Attribute class. The name is stored as a String.
     */
    private String name;

    /**
     * The type of the attribute.
     * <p>
     * This variable represents the type of the attribute. It is a private instance variable
     * in the Attribute class. The type is stored as a String.
     */
    private String type;

    /**
     * The value of an Attribute.
     * <p>
     * This variable represents the value of an attribute. It is a private instance variable
     * in the Attribute class. The value is stored as a String.
     */
    private String value;

    /**
     * The metadata variable holds a list of Metadata objects.
     * <p>
     * This variable is a private instance variable in the containing class. It stores a list
     * of Metadata objects. Each Metadata object represents a metadata attribute with a name,
     * type, and value.
     */
    private List<Metadata> metadata;

    public String asJson() {
        return "{\"name\":\"" + name + "\",\"type\":\"" + type + "\",\"value\":" + value + ",\"metadata\":[" + metadataAsJson() + "]}";
    }

    private String metadataAsJson() {
        return metadata.stream().map(Metadata::asJson).collect(Collectors.joining(","));
    }
}
