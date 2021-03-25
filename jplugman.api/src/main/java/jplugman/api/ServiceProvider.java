package jplugman.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.Optional;

/**
 * Provides some service given a service type
 */
public interface ServiceProvider {

    /**
     * @param serviceClass the class of the requested service
     * @param <T> the type of the requested service
     * @return a list of all the service implementing the requested service
     */
    <T> @NonNull ImmutableList<T> getAllServices(@NonNull Class<T> serviceClass);

    /**
     * @param serviceClass the class of the requested service
     * @param <T> the type of the requested service
     * @return a service implementing the requested  service.
     * If multiple services are available, one is peak at random.
     */
    default <T> @NonNull T getAnyService(@NonNull Class<T> serviceClass) {
        final var services = getAllServices(serviceClass);
        if (services.isEmpty()) {
            throw new ServiceNotFound(serviceClass);
        }
        return services.get(0);
    }

}
