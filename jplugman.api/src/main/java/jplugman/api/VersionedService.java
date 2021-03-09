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

    private final @NonNull Object service;

    private final @NonNull Version version;

    public @NonNull <T> Optional<T> getServiceAs(@NonNull Class<T> serviceClass) {
        if (serviceClass.isInstance(service)) {
            return Optional.of(serviceClass.cast(service));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "VersionedService{" +
                "service=" + service +
                ", version=" + version +
                '}';
    }
}
