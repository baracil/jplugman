package jplugman.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class Requirement<T> {

    /**
     * The class of the required service.
     * It must be an interface with the @{@link ApiVersion} annotation
     */
    @NonNull Class<T> serviceType;

    /**
     * The require api version of the service
     */
    int apiVersion;
}
