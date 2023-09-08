package de.app.fivegla.fiware.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.app.fivegla.fiware.model.Version;
import lombok.Getter;
import lombok.Setter;

/**
 * Response wrapper.
 */
@Getter
@Setter
public class VersionResponse {
    @JsonProperty("orion")
    private Version version;
}
