package Bastien Aracil.plugins.api;

import lombok.NonNull;

import java.util.Optional;

/**
 * Provides some service given a service type
 */
public interface ServiceProvider {

    <T> @NonNull Optional<T> findService(@NonNull Class<T> serviceType);

    default <T> @NonNull T getService(@NonNull Class<T> serviceType) {
        return findService(serviceType).orElseThrow(() -> new ServiceNotFound(serviceType));
    }

    static @NonNull ServiceProvider empty() {
        return new ServiceProvider() {
            @Override
            public @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType) {
                return Optional.empty();
            }
        };
    }

}
