package de.app.fivegla.fiware.model;

import de.app.fivegla.fiware.api.Validatable;
import de.app.fivegla.fiware.api.enums.Types;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Device location.
 * <p>
 * See <a href="https://www.rfc-editor.org/rfc/rfc7946.html">the RFC</a> for more information.
 */
@Getter
@Setter
@Builder
public class Location implements Validatable {

    /**
     * The type of the location.
     */
    private final String type = Types.Point.getKey();

    /**
     * The coordinates of the location.
     */
    private List<Double> coordinates;

    @Override
    public void validate() {
        if (coordinates == null || coordinates.size() != 2) {
            throw new IllegalArgumentException("The coordinates must be a list of two numbers.");
        }
    }
}
