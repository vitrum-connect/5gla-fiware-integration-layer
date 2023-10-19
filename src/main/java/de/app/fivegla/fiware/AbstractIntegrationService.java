package de.app.fivegla.fiware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.app.fivegla.fiware.api.FiwareIntegrationLayerException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

/**
 * Abstract integration service.
 */
@Slf4j
public abstract class AbstractIntegrationService<T> {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final String contextBrokerUrl;

    @Getter
    private final String tenant;

    public AbstractIntegrationService(String contextBrokerUrl, String tenant) {
        this.contextBrokerUrl = contextBrokerUrl;
        this.tenant = tenant;
    }

    /**
     * Returns the URL of the context broker.
     *
     * @return the URL of the context broker
     */
    String contextBrokerUrl() {
        return contextBrokerUrl;
    }

    /**
     * Returns the URL of the context broker for commands.
     *
     * @return the URL of the context broker for commands
     */
    String contextBrokerUrlForCommands() {
        return contextBrokerUrl + "/v2";
    }

    /**
     * Converts the current object to a JSON string representation.
     *
     * @param object the object to convert
     * @return a JSON string representing the current object
     * @throws FiwareIntegrationLayerException if an error occurs while transforming the object to JSON
     */
    String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writer().withDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new FiwareIntegrationLayerException("Could not transform object to JSON.", e);
        }
    }

    T toObject(String json) {
        try {
            var type = OBJECT_MAPPER.getTypeFactory()
                    .constructType(getEntityClass());
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new FiwareIntegrationLayerException("Could not transform JSON to object.", e);
        }
    }

    List<T> toListOfObjects(String json) {
        try {
            var type = OBJECT_MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, getEntityClass());
            return OBJECT_MAPPER.readValue(json, type);
        } catch (IOException e) {
            throw new FiwareIntegrationLayerException("Could not transform JSON to object.", e);
        }
    }

    Class<T> getEntityClass() {
        //noinspection unchecked
        return (Class<T>) ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


}
