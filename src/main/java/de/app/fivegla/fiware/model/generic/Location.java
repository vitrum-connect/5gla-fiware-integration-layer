package de.app.fivegla.fiware.model.generic;

import de.app.fivegla.fiware.api.FiwareTypes;
import lombok.Getter;
import lombok.Setter;

/**
 * The Attribute class represents an attribute with a name, type, and value.
 */
@Getter
@Setter
public class Location {

    /**
     * The value of an Attribute.
     * <p>
     * This variable represents the value of an attribute. It is a private instance variable
     * in the Attribute class. The value is stored as a String.
     */
    private String value;

    /**
     * The latitude of a location.
     * <p>
     * This variable represents the latitude of a location. It is a private instance variable
     * in the Location class which is a part of the Attribute class. The latitude is stored as a double.
     * The latitude is used along with the longitude to represent a geographical point in the Fiware platform.
     */
    private double latitude;

    /**
     * The longitude of a location.
     * <p>
     * This variable represents the longitude of a location. It is a private instance variable
     * in the Location class which is a part of the Attribute class. The longitude is stored as a double.
     * The longitude is used along with the latitude to represent a geographical point in the Fiware platform.
     */
    private double longitude;

    public String asJson() {
        return "{\"type\":\"" + FiwareTypes.GEO_POINT.getKey() + "\",\"value\":{\"type\":\"Point\",\"coordinates\":[" + longitude + "," + latitude + "]}}";
    }

}
