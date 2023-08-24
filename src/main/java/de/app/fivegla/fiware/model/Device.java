package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.enums.Type;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

/**
 * Device model.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/Device/doc/spec.md">the smart data models</a> for more information.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Device implements Validatable {

    /**
     * The type of the device.
     */
    private final String type = Type.Device.getKey();

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
