package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceClass;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class HashSetVersionedServiceProvider implements MutableVersionedServiceProvider {

    private final Set<VersionedService> services = new HashSet<>();

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull VersionedServiceClass<T> versionedServiceClass) {
        return services.stream()
                       .map(versionedServiceClass::castService)
                       .flatMap(Optional::stream)
                       .findFirst();
    }

    public void addVersionedService(@NonNull VersionedService versionedService) {
        services.add(versionedService);
    }


    public void removeVersionedService(@NonNull VersionedService versionedService) {
        services.remove(versionedService);
    }
}
