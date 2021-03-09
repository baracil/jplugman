package jplugman.api;

import lombok.NonNull;

import java.util.Optional;

/**
 * Provides some service given a service type
 */
public interface ServiceProvider {

    <T> @NonNull Optional<T> findService(@NonNull Class<T> serviceClass);

    default <T> @NonNull T getService(@NonNull Class<T> serviceClass) {
        return findService(serviceClass).orElseThrow(() -> new ServiceNotFound(serviceClass));
    }

}
