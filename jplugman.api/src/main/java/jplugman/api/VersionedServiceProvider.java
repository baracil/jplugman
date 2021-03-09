package jplugman.api;

import lombok.NonNull;

import java.util.Optional;

/**
 * Provides some service given a service type and a version
 */
public interface VersionedServiceProvider {

    /**
     * @param versionedServiceClass the class of the service with a version number
     * @param <T> the type of the service
     * @return an optional of an instantiation of the requested service if any exists, an empty optional otherwise
     */
    <T> @NonNull Optional<T> findService(@NonNull VersionedServiceClass<T> versionedServiceClass);

    /**
     * @param versionedServiceClass the class of the service with a version number
     * @return true if this provider has the requested service, false otherwise
     */
    default boolean hasService(VersionedServiceClass<?> versionedServiceClass) {
        return findService(versionedServiceClass).isPresent();
    }

    /**
     * @param after the provider to search if the search in this one failed
     * @return a {@link VersionedServiceProvider} that will search this provider first and then the one provided if the first search failed
     */
    default @NonNull VersionedServiceProvider thenSearch(@NonNull VersionedServiceProvider after) {
        return new VersionedServiceProvider() {
            @Override
            public @NonNull <T> Optional<T> findService(@NonNull VersionedServiceClass<T> versionedServiceClass) {
                return VersionedServiceProvider.this.findService(versionedServiceClass).or(() -> after.findService(
                        versionedServiceClass));
            }
        };
    }


}
