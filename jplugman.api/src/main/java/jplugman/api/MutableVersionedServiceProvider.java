package jplugman.api;

import lombok.NonNull;
import jplugman.api.VersionedService;
import jplugman.api.VersionedServiceProvider;

public interface MutableVersionedServiceProvider extends VersionedServiceProvider {

    void addVersionedService(@NonNull VersionedService versionedService);

    void removeVersionedService(@NonNull VersionedService versionedService);

}
