package Bastien Aracil.plugins.api;

import lombok.NonNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class MutableVersionedServiceProvider implements VersionedServiceProvider {

    private final Set<VersionedService> services = new HashSet<>();

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull Requirement<T> requirement) {
        return services.stream()
                       .map(requirement::extractService)
                       .flatMap(Optional::stream)
                       .findFirst();
    }

    public void addVersionedService(@NonNull VersionedService versionedService) {
        services.add(versionedService);
    }


}
