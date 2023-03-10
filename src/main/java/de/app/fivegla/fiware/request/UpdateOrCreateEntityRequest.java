package de.app.fivegla.fiware.request;

import de.app.fivegla.fiware.api.enums.ActionType;
import lombok.Builder;

import java.util.List;

/**
 * Request.
 */
@Builder
public class UpdateOrCreateEntityRequest {

    private final String actionType = ActionType.APPEND.getKey();
    private List<Object> entities;

}
