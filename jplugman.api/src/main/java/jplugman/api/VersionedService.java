package jplugman.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * A service with a version
 */
@RequiredArgsConstructor
@Getter
public class VersionedService {

    /**
     * The type of the service
     */
    private final @NonNull Class<?> type;

    /**
     * The instanciation of the service. service is an instance of type.
     */
    private final @NonNull Object service;

    /**
     * The version of the service
     */
    private final @NonNull Version version;

    public @NonNull <T> Optional<T> getServiceAs(@NonNull Class<T> serviceClass) {
        if (serviceClass.isAssignableFrom(type)) {
            return Optional.of(serviceClass.cast(service));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "VersionedService{" +
                "type="+type+
                ", service=" + service +
                ", version=" + version +
                '}';
    }
}
