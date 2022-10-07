package jplugman.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Optional;

/**
 * A service with a version.
 */
@RequiredArgsConstructor
@Value
public class VersionedService {


    /**
     * The class that the service provides.
     * This is not necessarily exactly the class of
     * the member <code>service</code> (but it is
     * a super of it).
     */
    @NonNull Class<?> serviceType;

    /**
     * the service
     */
    @NonNull Object service;

    /**
     * The version of this service
     */
    @Getter
    int majorVersion;

    /**
     * @param clazz the class the service should be cast to
     * @param <U> the type of the class the service should be cast to
     * @return an optional containing the cast service if the requested
     * class is <br>the same as</br> the one provided by this
     */
    public <U> @NonNull Optional<U> getInstanceAs(@NonNull Class<U> clazz) {
        if (serviceType.equals(clazz)) {
            return Optional.of(clazz.cast(service));
        }
        return Optional.empty();
    }

}
