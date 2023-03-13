package de.app.fivegla.fiware.model;

import de.app.fivegla.fiware.api.enums.Types;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Device category model.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/Device/doc/spec.md">the smart data models</a> for more information.
 */
@Getter
@Setter
@Builder
public class DeviceCategory {

    /**
     * The type of the device category.
     */
    private final String type = Types.Text.getKey();

    /**
     * The value of the device category.
     */
    private List<String> value;
}
