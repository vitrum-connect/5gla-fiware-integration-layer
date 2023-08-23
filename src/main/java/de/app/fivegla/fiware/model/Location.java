package de.app.fivegla.fiware.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import de.app.fivegla.fiware.model.api.Validatable;
import de.app.fivegla.fiware.model.enums.Type;
import lombok.*;

import java.util.List;

/**
 * Device location.
 * <p>
 * See <a href="https://www.rfc-editor.org/rfc/rfc7946.html">the RFC</a> for more information.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Location implements Validatable {

    /**
     * The type of the location.
     */
    private final String type = Type.Point.getKey();

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
