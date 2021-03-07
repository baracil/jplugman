package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceProvider;

public interface MutableVersionedServiceProvider extends VersionedServiceProvider {

    void addVersionedService(@NonNull VersionedService versionedService);

    void removeVersionedService(@NonNull VersionedService versionedService);

}
