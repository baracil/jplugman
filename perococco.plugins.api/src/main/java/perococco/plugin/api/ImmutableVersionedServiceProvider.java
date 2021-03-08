package Bastien Aracil.plugin.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ImmutableVersionedServiceProvider implements VersionedServiceProvider {

    /**
     * @param versionedServices the services
     * @return an immutable {@link VersionedServiceProvider} that uses the provided services
     */
    public static @NonNull VersionedServiceProvider of(@NonNull ImmutableSet<VersionedService> versionedServices) {
        return new ImmutableVersionedServiceProvider(versionedServices);
    }

    /**
     * @return an immutable {@link VersionedServiceProvider} that provides no service
     */
    public static @NonNull VersionedServiceProvider of() {
        return of(ImmutableSet.of());
    }

    /**
     * same {@link #of(ImmutableSet)} but with varargs arguments
     */
    public static @NonNull VersionedServiceProvider of(VersionedService...versionedServices) {
        return of(ImmutableSet.copyOf(versionedServices));
    }



    private final @NonNull ImmutableSet<VersionedService> services;

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull VersionedServiceClass<T> versionedServiceClass) {
        return services.stream()
                       .map(versionedServiceClass::castService)
                       .flatMap(Optional::stream)
                       .findFirst();
    }

}
