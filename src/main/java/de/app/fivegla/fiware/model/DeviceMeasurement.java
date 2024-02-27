package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.generic.Attribute;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Device measurement.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/DeviceMeasurement/README.md">...</a> for more information.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceMeasurement implements Validatable {

    /**
     * The ID of the device measurement.
     */
    private String id;

    /**
     * The type of the device measurement.
     */
    private String type;

    /**
     * The location of the device.
     */
    private Attribute location;

    /**
     * The Attribute class represents an attribute with a name, type, and value.
     */
    private Attribute measurement;

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the device measurement must not be null or blank.");
        }
        if (location == null) {
            throw new IllegalArgumentException("The location of the device measurement must not be null.");
        }
        if (measurement == null) {
            throw new IllegalArgumentException("The measurement of the device measurement must not be null.");
        }
    }

    public String asJson() {
        return "{\"id\":\"" + id + "\",\"type\":\"" + type + "\",\"location\":" + location.asJson() + ",\"measurement\":" + measurement.asJson() + "}";
    }
}
