package de.app.fivegla.fiware.model.generic;

import de.app.fivegla.fiware.api.FiwareType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * The Attribute class represents an attribute with a name, type, and value.
 */
@Getter
@Setter
public class Attribute {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.systemDefault());

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
     * The dateCreated represents the timestamp when an Attribute object is created.
     * It is a private instance variable in the Attribute class.
     * The date is stored as an Instant, which represents a moment on the timeline in UTC time zone.
     */
    private Instant dateCreated;

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
        return "{" +
                "  \"name\":\"" + name + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"value\":" + value + "," +
                "  \"dateCreated\":" + dateCreatedAsJson() + "," +
                "  \"location\":" + locationAsJson() +
                "}";
    }

    private String dateCreatedAsJson() {
        return "{" +
                "  \"type\":\"" + FiwareType.DATE_TIME.getKey() + "\"," +
                "  \"value\":\"" + formatter.format(dateCreated) + "\"" +
                "}";
    }

    private String locationAsJson() {
        return "{" +
                "  \"type\":\"" + FiwareType.GEO_POINT.getKey() + "\"," +
                "  \"value\": {" +
                "    \"type\":\"Point\"," +
                "    \"coordinates\": [" + longitude + "," + latitude + "]" +
                "  }" +
                "}";
    }
}
