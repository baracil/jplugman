package Bastien Aracil.plugin.manager.impl;

import lombok.NonNull;
import Bastien Aracil.plugin.api.VersionedService;
import Bastien Aracil.plugin.api.VersionedServiceClass;

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
