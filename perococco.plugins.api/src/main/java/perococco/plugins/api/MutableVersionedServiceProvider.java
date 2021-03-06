package Bastien Aracil.plugins.api;

import lombok.NonNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public interface MutableVersionedServiceProvider extends VersionedServiceProvider {

    void addVersionedService(@NonNull VersionedService versionedService);

    void removeVersionedService(@NonNull VersionedService versionedService);

}
