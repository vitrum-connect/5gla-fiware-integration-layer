package de.app.fivegla.fiware.request;

import de.app.fivegla.fiware.request.enums.ActionType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Request.
 */
@Getter
@Setter
@Builder
public class UpdateOrCreateEntityRequest {

    /**
     * The action type.
     */
    private final String actionType = ActionType.APPEND.getKey();

    /**
     * The entities.
     */
    private List<Object> entities;

}
