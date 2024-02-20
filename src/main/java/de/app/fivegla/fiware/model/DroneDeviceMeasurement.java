package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.enums.Type;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Drone device measurement.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DroneDeviceMeasurement implements Validatable {

    /**
     * The type of the device measurement.
     */
    private final String type = Type.DroneDeviceMeasurement.getKey();

    /**
     * The ID of the device.
     */
    private String id;

    /**
     * The image path of the drone device measurement.
     */
    private String channel;

    /**
     * The image path of the drone device measurement.
     */
    private String imagePath;

    /**
     * The device measurement of the drone device measurement.
     */
    private DeviceMeasurement deviceMeasurement;

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the drone device measurement must not be blank.");
        }
        if (StringUtils.isBlank(channel)) {
            throw new IllegalArgumentException("The channel of the drone device measurement must not be blank.");
        }
        if (StringUtils.isBlank(imagePath)) {
            throw new IllegalArgumentException("The image path of the drone device measurement must not be blank.");
        }
        if (deviceMeasurement == null) {
            throw new IllegalArgumentException("The device measurement of the drone device measurement must not be null.");
        }
        deviceMeasurement.validate();
    }
}
