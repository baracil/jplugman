package jplugman.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class Requirement<T> {

    /**
     * The class of the required service.
     */
    @NonNull Class<T> serviceType;

    /**
     * The required major version of the service
     */
    int majorVersion;
}
