package Bastien Aracil.plugins.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

//TODO find a better name. This class is used as a requirement and a provided service type
@Value
public class Requirement<T> {

    /**
     * The type of the required service
     */
    @NonNull Class<T> serviceType;

    /**
     * The version of the required service
     */
    int version;

    public @NonNull Optional<T> extractService(@NonNull VersionedService versionedService) {
        if (versionedService.getVersion() == version) {
            return versionedService.getServiceAs(serviceType);
        }
        return Optional.empty();
    }
}
