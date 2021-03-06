package Bastien Aracil.plugins.api;

import com.google.common.collect.ImmutableSet;
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

    /**
     * @param versionedServices the services
     * @return an immutable {@link VersionedServiceProvider} that uses the provided services
     */
    static @NonNull VersionedServiceProvider of(@NonNull ImmutableSet<VersionedService> versionedServices) {
        return new ImmutableVersionedServiceProvider(versionedServices);
    }

    /**
     * @return an immutable {@link VersionedServiceProvider} that provides no service
     */
    static @NonNull VersionedServiceProvider of() {
        return of(ImmutableSet.of());
    }

    /**
     * same {@link #of(ImmutableSet)} but with varargs arguments
     */
    static @NonNull VersionedServiceProvider of(VersionedService...versionedServices) {
        return of(ImmutableSet.copyOf(versionedServices));
    }


    /**
     * @param after the provider to search if the search in this one failed
     * @return a {@link VersionedServiceProvider} that will search this provider first and then the one provided if the first search failed
     */
    default @NonNull VersionedServiceProvider thenSearch(@NonNull VersionedServiceProvider after) {
        return new VersionedServiceProvider() {
            @Override
            public @NonNull <T> Optional<T> findService(@NonNull VersionedServiceType<T> versionedServiceType) {
                return VersionedServiceProvider.this.findService(versionedServiceType).or(() -> after.findService(versionedServiceType));
            }
        };
    }


}
