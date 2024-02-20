package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.enums.Type;
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
     * The type of the device measurement.
     */
    private final String type = Type.DeviceMeasurement.getKey();

    /**
     * The ID of the device.
     */
    private String id;

    /**
     * The manufacturer ID of the device.
     */
    private String manufacturerSpecificId;

    /**
     * The category of the device.
     */
    private DeviceCategory deviceCategory;

    /**
     * The location of the device.
     */
    private Location location;

    /**
     * The numeric value of the device measurement.
     */
    private double numValue;

    /**
     * The controlled property of the device measurement.
     */
    private String controlledProperty;

    /**
     * The date of the device measurement.
     */
    private String dateObserved;

    /**
     * The unit of the device measurement.
     */
    private String unit;

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the device measurement must not be null or blank.");
        }
        if (location == null) {
            throw new IllegalArgumentException("The location of the device measurement must not be null.");
        } else {
            location.validate();
        }
        if (StringUtils.isBlank(manufacturerSpecificId)) {
            throw new IllegalArgumentException("The manufacturer specific id of the device must not be null.");
        }
        if (deviceCategory == null) {
            throw new IllegalArgumentException("The device category of the device must not be null.");
        }
    }
}
