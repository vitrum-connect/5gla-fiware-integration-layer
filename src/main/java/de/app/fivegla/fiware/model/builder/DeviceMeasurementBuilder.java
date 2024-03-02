package de.app.fivegla.fiware.model.builder;

import de.app.fivegla.fiware.api.FiwareChecker;
import de.app.fivegla.fiware.api.FiwareType;
import de.app.fivegla.fiware.model.DeviceMeasurement;

import java.time.Instant;

/**
 * The DeviceMeasurementBuilder class is responsible for building instances of the DeviceMeasurement class.
 * It provides methods to set the id and type of the device measurement.
 */
public final class DeviceMeasurementBuilder {

    private final DeviceMeasurement deviceMeasurement;


    /**
     * The DeviceMeasurementBuilder class is responsible for building instances of the DeviceMeasurement class.
     * It provides methods to set the id and type of the device measurement.
     */
    public DeviceMeasurementBuilder() {
        deviceMeasurement = new DeviceMeasurement();
    }

    /**
     * Sets the ID of the device measurement.
     *
     * @param id the ID to be set for the device measurement
     * @return the DeviceMeasurementBuilder instance
     */
    public DeviceMeasurementBuilder withId(String id) {
        FiwareChecker.checkId(id);
        deviceMeasurement.setId(id);
        return this;
    }

    /**
     * Sets a measurement attribute for the DeviceMeasurement object.
     *
     * @param name        the name of the measurement attribute
     * @param fiwareType  the fiwareType of the measurement attribute
     * @param value       the value of the measurement attribute
     * @param dateCreated the date observed of the measurement attribute
     * @return the DeviceMeasurementBuilder instance with the updated measurement attribute
     */
    public DeviceMeasurementBuilder withMeasurement(String name, FiwareType fiwareType, double value, Instant dateCreated, double latitude, double longitude) {
        deviceMeasurement.setName(name);
        deviceMeasurement.setType(fiwareType.getKey());
        deviceMeasurement.setValue(value);
        deviceMeasurement.setDateCreated(dateCreated);
        deviceMeasurement.setLatitude(latitude);
        deviceMeasurement.setLongitude(longitude);
        return this;
    }

    /**
     * Sets the external data reference for the device measurement.
     *
     * @param externalDataReference the external data reference to be set
     * @return the DeviceMeasurementBuilder instance
     */
    public DeviceMeasurementBuilder withExternalDataReference(String externalDataReference) {
        deviceMeasurement.setExternalDataReference(externalDataReference);
        return this;
    }

    /**
     * Sets the type of the device measurement.
     *
     * @param type the type to be set for the device measurement
     * @return the DeviceMeasurementBuilder instance
     */
    public DeviceMeasurementBuilder withType(String type) {
        deviceMeasurement.setType(type);
        return this;
    }

    public DeviceMeasurement build() {
        deviceMeasurement.validate();
        return deviceMeasurement;
    }

}
