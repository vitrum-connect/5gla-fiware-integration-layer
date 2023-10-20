package de.app.fivegla.fiware;

import java.util.List;

/**
 * This class is an abstract base class for IT (Integration Testing) classes.
 * It provides common properties and methods that can be used by concrete IT classes.
 */
public class AbstractIT {
    protected String contextBrokerUrl = "http://localhost:1026";
    protected String tenant = "default";
    protected List<String> notificationUrls = List.of("http://192.168.56.1:5055/notify");

}
