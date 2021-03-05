package Bastien Aracil.plugins.api;

import lombok.NonNull;

import java.util.Optional;

/**
 * Provides some service given a service type and a version
 */
public interface VersionedServiceProvider {

    <T> @NonNull Optional<T> findService(@NonNull VersionedServiceType<T> versionedServiceType);

    default boolean hasService(VersionedServiceType<?> versionedServiceType) {
        return findService(versionedServiceType).isPresent();
    }
}
