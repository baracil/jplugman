package jplugman.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * A service with a version.
 */
@RequiredArgsConstructor
public class VersionedService {


    /**
     * The class that the service provides.
     * This is not necessarily exactly the class of
     * the membre <code>service</code> (but it is
     * a super of it).
     */
    private final @NonNull Class<?> serviceType;

    /**
     * the service
     */
    private final @NonNull Object service;

    /**
     * The version of this service
     */
    @Getter
    private final int majorVersion;

    /**
     * @param clazz the class the service should be casted to
     * @param <U> the type of the class the service should be casted to
     * @return an optional containing the casted service if the requested
     * class is <br>the same as</br> the one provided by this
     */
    public <U> @NonNull Optional<U> getInstanceAs(@NonNull Class<U> clazz) {
        if (serviceType.equals(clazz)) {
            return Optional.of(clazz.cast(service));
        }
        return Optional.empty();
    }

}
