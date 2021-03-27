package Bastien Aracil.jplugman.manager;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Requirement;
import jplugman.api.VersionedService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ImmutableVersionedServiceProvider implements VersionedServiceProvider {

    private final @NonNull ImmutableSet<VersionedService<?>> services;

    @Override
    public @NonNull <T> Stream<T> findService(@NonNull Requirement<T> requirement) {
        return services.stream()
                       .filter(s -> requirement.getMajorVersion() == s.getMajorVersion())
                       .map(s -> s.getInstanceAs(requirement.getServiceType()))
                       .flatMap(Optional::stream);
    }

    /**
     * @param versionedServices the services
     * @return an immutable {@link VersionedServiceProvider} that uses the provided services
     */
    public static @NonNull VersionedServiceProvider of(@NonNull ImmutableSet<VersionedService<?>> versionedServices) {
        return new ImmutableVersionedServiceProvider(versionedServices);
    }

    /**
     * @return an immutable {@link VersionedServiceProvider} that provides no service
     */
    public static @NonNull VersionedServiceProvider of() {
        return of(ImmutableSet.of());
    }


}
