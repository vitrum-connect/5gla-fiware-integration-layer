package de.app.fivegla.fiware.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Device model.
 * <p>
 * See <a href="https://github.com/smart-data-models/dataModel.Device/blob/master/Device/doc/spec.md">the smart data models</a> for more information.
 * <p>
 */
@Getter
@Setter
@Builder
public class Device {

    private String id;
    private final String type = DeviceTypeValues.Device.getKey();
    private DeviceCategory deviceCategory;

}
