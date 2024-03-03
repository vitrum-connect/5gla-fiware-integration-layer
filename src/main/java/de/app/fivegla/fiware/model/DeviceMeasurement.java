package de.app.fivegla.fiware.model;

import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.internal.Attribute;
import org.apache.commons.lang3.StringUtils;

/**
 * Device measurement.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/DeviceMeasurement/README.md">...</a> for more information.
 */
public record DeviceMeasurement(
        String id,
        String type,
        Attribute name,
        Attribute controlledProperty,
        Attribute dateCreated,
        Attribute externalDataReference,
        double latitude,
        double longitude
) implements Validatable {


    public String asJson() {
        validate();
        return "{" +
                "  \"id\":\"" + id + "\"," +
                "  \"type\":\"" + type + "\"," +
                "  \"name\":" + name.asJson() + "," +
                "  \"controlledProperty\":" + controlledProperty.asJson() + "," +
                "  \"externalDataReference\":" + externalDataReference.asJson() + "," +
                "  \"dateCreated\":" + dateCreated.asJson() + "," +
                "  \"location\":" + locationAsJson() +
                "}";
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
