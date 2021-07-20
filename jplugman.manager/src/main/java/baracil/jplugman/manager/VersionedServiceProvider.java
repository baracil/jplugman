package baracil.jplugman.manager;

import jplugman.api.Requirement;
import lombok.NonNull;

import java.util.stream.Stream;

/**
 * Provides services fulfilling a {@link Requirement}
 */
public interface VersionedServiceProvider {

    /**
     * @param requirement the requirement the service must fulfill
     * @param <T> the type of the service
     * @return a stream of all the service fulfilling the provided requirement
     */
    <T> @NonNull Stream<T> findService(@NonNull Requirement<T> requirement);

    /**
     * @param requirement the requirement to fulfill
     * @return true if this provider has a service fulfilling the requested requirement, false otherwise
     */
    default boolean hasExtensionPoint(@NonNull Requirement<?> requirement) {
        return findService(requirement).findAny().isPresent();
    }

    /**
     * @param other the provider to search as well
     * @return a {@link VersionedServiceProvider} that will search both this provider and the one provided as parameter
     */
    default @NonNull VersionedServiceProvider thenSearch(@NonNull VersionedServiceProvider other) {
        return new VersionedServiceProvider() {
            @Override
            public @NonNull <T> Stream<T> findService(@NonNull Requirement<T> requirement) {
                return Stream.concat(
                        VersionedServiceProvider.this.findService(requirement),
                        other.findService(requirement)
                );
            }
        };
    }

}
