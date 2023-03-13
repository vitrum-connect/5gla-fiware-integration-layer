package de.app.fivegla.fiware.model;

import de.app.fivegla.fiware.api.Validatable;
import de.app.fivegla.fiware.api.enums.Types;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Device measurement.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/DeviceMeasurement/README.md">...</a> for more information.
 */
@Getter
@Setter
@Builder
public class DeviceMeasurement implements Validatable {

    /**
     * The type of the device measurement.
     */
    private final String type = Types.DeviceMeasurement.getKey();

    /**
     * The ID of the device measurement.
     */
    private String id;

    /**
     * The location of the device measurement.
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
     * The device of the device measurement.
     */
    private String refDevice;

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
        if (StringUtils.isBlank(refDevice)) {
            throw new IllegalArgumentException("The device reference of the device measurement must not be null or blank.");
        }
        if (location == null) {
            throw new IllegalArgumentException("The location of the device measurement must not be null.");
        } else {
            location.validate();
        }
    }
}
