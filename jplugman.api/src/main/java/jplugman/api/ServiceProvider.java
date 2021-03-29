package jplugman.api;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

/**
 * Registry of services, provides a list of services for a given class
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
     * @return a service implementing the requested service if and only if only one
     * implementation is available.
     */
    default <T> @NonNull T getSingleService(@NonNull Class<T> serviceClass) {
        final var services = getAllServices(serviceClass);
        if (services.isEmpty()) {
            throw new ServiceNotFound(serviceClass);
        } else if (services.size() > 1) {
            throw new MultipleServiceFound(serviceClass, services.stream().map(s -> s.getClass().getName()).collect(ImmutableList.toImmutableList()));
        }
        return services.get(0);
    }

    /**
     * @param serviceClass the class of the requested service
     * @param <T> the type of the requested service
     * @return a service implementing the requested  service.
     * If multiple services are available, one is peaked at random.
     */
    default <T> @NonNull T getAnyService(@NonNull Class<T> serviceClass) {
        final var services = getAllServices(serviceClass);
        if (services.isEmpty()) {
            throw new ServiceNotFound(serviceClass);
        }
        return services.get(0);
    }

}
