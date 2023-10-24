package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.enums.Type;
import lombok.*;

import java.util.List;

/**
 * Device category model.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/Device/doc/spec.md">the smart data models</a> for more information.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeviceCategory {

    /**
     * The type of the device category.
     */
    private final String type = Type.Text.getKey();

    /**
     * The value of the device category.
     */
    private List<String> value;

}
