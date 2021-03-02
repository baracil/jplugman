package Bastien Aracil.plugins.manager;

import lombok.NonNull;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceProvider;

public interface Application {

    /**
     * The application version the plugins will be plugged to. This
     * is checked against {@link Plugin#getVersionCompatibility()}.
     */
    int getVersion();

    @NonNull VersionedServiceProvider getServiceProvider();

    void attachService(@NonNull VersionedService versionedService);
}
