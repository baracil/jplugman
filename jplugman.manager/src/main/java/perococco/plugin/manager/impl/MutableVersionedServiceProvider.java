package Bastien Aracil.plugin.manager.impl;

import lombok.NonNull;
import Bastien Aracil.plugin.api.VersionedService;
import Bastien Aracil.plugin.api.VersionedServiceProvider;

public interface MutableVersionedServiceProvider extends VersionedServiceProvider {

    void addVersionedService(@NonNull VersionedService versionedService);

    void removeVersionedService(@NonNull VersionedService versionedService);

}
