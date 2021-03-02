package Bastien Aracil.plugins.api;

import lombok.NonNull;
import lombok.Value;

@Value
public class Requirement {

    /**
     * The type of the required service
     */
    @NonNull Class<?> serviceType;

    /**
     * The version of the required service
     */
    @NonNull int version;
}
