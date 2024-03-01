package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.generic.Attribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

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
     * The Attribute class represents an attribute with a name, type, and value.
     */
    private Attribute measurement;

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the device measurement must not be null or blank.");
        }
        if (measurement == null) {
            throw new IllegalArgumentException("The measurement of the device measurement must not be null.");
        }
    }

    public String asJson() {
        return "{\"id\":\"" + id + "\",\"type\":\"" + type + "\",\"measurement\":" + measurement.asJson() + "}";
    }
}
