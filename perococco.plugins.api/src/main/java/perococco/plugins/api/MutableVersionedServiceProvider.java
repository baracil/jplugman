package Bastien Aracil.plugins.api;

import lombok.NonNull;

public interface MutableVersionedServiceProvider extends VersionedServiceProvider {

    void addVersionedService(@NonNull VersionedService versionedService);

    void removeVersionedService(@NonNull VersionedService versionedService);

}
