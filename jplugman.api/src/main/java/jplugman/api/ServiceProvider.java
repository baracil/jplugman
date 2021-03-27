package jplugman.api;

import lombok.NonNull;

import java.util.stream.Stream;

/**
 * Provides some service given a service type and a version
 */
public interface ServiceProvider {

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
    default boolean hasService(Requirement<?> requirement) {
        return findService(requirement).findAny().isPresent();
    }

    /**
     * @param other the provider to search as well
     * @return a {@link ServiceProvider} that will search this provider first and then the other one
     */
    default @NonNull ServiceProvider thenSearch(@NonNull ServiceProvider other) {
        return new ServiceProvider() {
            @Override
            public @NonNull <T> Stream<T> findService(@NonNull Requirement<T> requirement) {
                return Stream.concat(
                        ServiceProvider.this.findService(requirement),
                        other.findService(requirement)
                );
            }
        };
    }

    ServiceProvider EMPTY = new ServiceProvider() {
        @Override
        public @NonNull <T> Stream<T> findService(@NonNull Requirement<T> requirement) {
            return Stream.empty();
        }
    };


}
