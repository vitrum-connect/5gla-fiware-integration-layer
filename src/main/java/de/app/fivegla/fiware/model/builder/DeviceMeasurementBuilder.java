package de.app.fivegla.fiware.model.builder;

import de.app.fivegla.fiware.api.FiwareChecker;
import de.app.fivegla.fiware.api.FiwareTypes;
import de.app.fivegla.fiware.model.DeviceMeasurement;
import de.app.fivegla.fiware.model.generic.Attribute;
import de.app.fivegla.fiware.model.generic.Metadata;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The DeviceMeasurementBuilder class is responsible for building instances of the DeviceMeasurement class.
 * It provides methods to set the id and type of the device measurement.
 */
public final class DeviceMeasurementBuilder {

    private final DeviceMeasurement deviceMeasurement;
    private final DateTimeFormatter formatter = DateTimeFormatter
            // The supported format for the FIWARE Context Broker is "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'".
            // 2017-06-17T07:21:24.238Z
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.systemDefault());

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
        attribute.setName("location");
        attribute.setType(FiwareTypes.GEO_POINT.getKey());
        attribute.setValue("{\"type\":\"Point\",\"coordinates\":[" + longitude + "," + latitude + "]}");
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
    public DeviceMeasurementBuilder withMeasurement(String name, String type, String value, Instant dateObserved, MetadataEntry... metadataEntries) {
        var measurement = new Attribute();
        measurement.setName(name);
        measurement.setType(type);
        measurement.setValue(value);
        var metadata = new ArrayList<Metadata>();
        var dateObservedMetadata = new Metadata();
        dateObservedMetadata.setName("dateObserved");
        dateObservedMetadata.setType(FiwareTypes.DATE_TIME.getKey());
        dateObservedMetadata.setValue(formatter.format(dateObserved));
        metadata.add(dateObservedMetadata);
        if (null != metadataEntries && metadataEntries.length > 0) {
            for (var metadataEntry : metadataEntries) {
                metadata.add(metadataEntry.asMetadata());
            }
            measurement.setMetadata(metadata);
        }
        deviceMeasurement.setMeasurement(measurement);
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

    public record MetadataEntry(String name, String type, String value) {
        public Metadata asMetadata() {
            var metadata = new Metadata();
            metadata.setName(name);
            metadata.setType(type);
            metadata.setValue(value);
            return metadata;
        }
    }

}
