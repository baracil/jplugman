package jplugman.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * A service with a version.
 */
@RequiredArgsConstructor
public class VersionedService<T> {

    /**
     * the service
     */
    @Getter
    private final @NonNull T service;

    /**
     * The version of this service
     */
    @Getter
    private final @NonNull Version version;

    /**
     * @param clazz the class the service should be casted to
     * @param <U> the type of the class the service should be casted to
     * @return an optional containing the casted service
     * if it is an instance of <code>clazz</code>, an empty optional otherwise
     */
    public <U> @NonNull Optional<U> getInstanceAs(@NonNull Class<U> clazz) {
        if (clazz.isInstance(service)) {
            return Optional.of(clazz.cast(service));
        }
        return Optional.empty();
    }

    /**
     * @return the class of the service
     */
    public @NonNull Class<?> getInstanceClass() {
        return service.getClass();
    }

    public int getMajorVersion() {
        return version.getMajor();
    }
}
