package Bastien Aracil.plugins.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ImmutableVersionedServiceProvider implements VersionedServiceProvider {

    private final @NonNull ImmutableSet<VersionedService> services;

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull VersionedServiceType<T> versionedServiceType) {
        return services.stream()
                       .map(versionedServiceType::castService)
                       .flatMap(Optional::stream)
                       .findFirst();
    }

}
