package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.api.Validatable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Device measurement.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/DeviceMeasurement/README.md">...</a> for more information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceMeasurement implements Validatable {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.systemDefault());

    /**
     * The ID of the device measurement.
     */
    private String id;

    /**
     * The type of the device measurement.
     */
    private String type;

    /**
     * The name of an attribute.
     * <p>
     * This variable represents the name of an attribute. It is a private instance variable
     * in the Attribute class. The name is stored as a String.
     */
    private String name;

    /**
     * The value of an Attribute.
     * <p>
     * This variable represents the value of an attribute. It is a private instance variable
     * in the Attribute class. The value is stored as a String.
     */
    private double value;

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

    /**
     * The externalDataReference variable holds a reference to external data related to a device measurement.
     * <p>
     * This variable is of type String, and it is declared as private in the DeviceMeasurement class.
     * <p>
     * This variable is used to identify and reference external data associated with a device measurement. It can be used to link the measurement to data stored in an external system
     * or to provide additional context or information about the measurement from an external source.
     * <p>
     * The value stored in this variable should be a valid reference to the external data, such as an identifier, URL, or any other format used to reference the data.¶* <p>
     */
    private String externalDataReference;

    public String asJson() {
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"name\":\"" + name + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"value\":" + value + "," +
                "  \"externalDataLink\":" + externalDataReferenceAsJson() + "," +
                "  \"dateCreated\":" + dateCreatedAsJson() + "," +
                "  \"location\":" + locationAsJson() +
                "}";
    }

    private String externalDataReferenceAsJson() {
        if (StringUtils.isBlank(externalDataReference)) {
            return "{}";
        } else {
            return "{" +
                    "  \"type\":\"" + FiwareType.TEXT.getKey() + "\"," +
                    "  \"value\":\"" + externalDataReference + "\"" +
                    "}";
        }
    }

    private String dateCreatedAsJson() {
        if (null == dateCreated) {
            return "{}";
        } else {
            return "{" +
                    "  \"type\":\"" + FiwareType.DATE_TIME.getKey() + "\"," +
                    "  \"value\":\"" + formatter.format(dateCreated) + "\"" +
                    "}";
        }
    }

    private String locationAsJson() {
        if (latitude == 0.0 && longitude == 0.0) {
            return "{}";
        } else {
            return "{" +
                    "  \"type\":\"" + FiwareType.GEO_POINT.getKey() + "\"," +
                    "  \"value\": {" +
                    "    \"type\":\"Point\"," +
                    "    \"coordinates\": [" + longitude + "," + latitude + "]" +
                    "  }" +
                    "}";
        }
    }

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the device measurement must not be null or blank.");
        }
        if (StringUtils.isBlank(type)) {
            throw new IllegalArgumentException("The type of the device measurement must not be null or blank.");
        }
    }

}
