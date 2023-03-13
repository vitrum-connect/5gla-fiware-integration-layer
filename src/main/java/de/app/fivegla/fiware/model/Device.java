package de.app.fivegla.fiware.model;

import de.app.fivegla.fiware.api.Validatable;
import de.app.fivegla.fiware.api.enums.Types;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Device model.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/Device/doc/spec.md">the smart data models</a> for more information.
 */
@Getter
@Setter
@Builder
public class Device implements Validatable {

    /**
     * The type of the device.
     */
    private final String type = Types.Device.getKey();

    /**
     * The ID of the device.
     */
    private String id;

    /**
     * The category of the device.
     */
    private DeviceCategory deviceCategory;

    @Override
    public void validate() {
        if (StringUtils.isBlank(id)) {
            throw new IllegalArgumentException("The id of the device must not be null.");
        }
    }
}
