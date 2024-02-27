package de.app.fivegla.fiware.model.builder;

import de.app.fivegla.fiware.api.FiwareChecker;
import de.app.fivegla.fiware.api.FiwareTypes;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.generic.Attribute;
import de.app.fivegla.fiware.model.generic.Metadata;

import java.time.Instant;
import java.util.List;

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
     * Sets the latitude and longitude of the device's location.
     *
     * @param latitude  the latitude of the device's location
     * @param longitude the longitude of the device's location
     * @return the DeviceMeasurementBuilder instance with the updated location
     */
    public DeviceMeasurementBuilder withLocation(double latitude, double longitude) {
        var attribute = new Attribute();
        attribute.setValue("{\"type\":\"Point\",\"coordinates\":[" + longitude + "," + latitude + "]}");
        attribute.setType(FiwareTypes.GEO_POINT.getKey());
        deviceMeasurement.setLocation(attribute);
        return this;
    }

    /**
     * Sets a measurement attribute for the DeviceMeasurement object.
     *
     * @param name         the name of the measurement attribute
     * @param type         the type of the measurement attribute
     * @param value        the value of the measurement attribute
     * @param dateObserved the date observed of the measurement attribute
     * @return the DeviceMeasurementBuilder instance with the updated measurement attribute
     */
    public DeviceMeasurementBuilder withMeasurement(String name, String type, String value, Instant dateObserved) {
        var attribute = new Attribute();
        attribute.setName(name);
        attribute.setType(type);
        attribute.setValue(value);
        var metadata = new Metadata();
        metadata.setName("dateObserved");
        metadata.setType(FiwareTypes.DATE_TIME.getKey());
        metadata.setValue(dateObserved.toString());
        attribute.setMetadata(List.of(metadata));
        deviceMeasurement.setMeasurement(attribute);
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
        return deviceMeasurement;
    }

}
