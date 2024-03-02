package de.app.fivegla.fiware.model.generic;

import de.app.fivegla.fiware.api.FiwareType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * The Attribute class represents an attribute with a name, type, and value.
 */
@Getter
@Setter
public class Attribute {

    private final DateTimeFormatter formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneId.systemDefault());


}
