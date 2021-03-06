package Bastien Aracil.plugins.manager;

import lombok.NonNull;
import Bastien Aracil.plugins.api.*;

public interface Application {

    /**
     * The application version the plugins will be plugged to. This
     * is checked against {@link Plugin#getApplicationVersion()}.
     */
    @NonNull Version getVersion();

    /**
     * @param pluginServiceType the type of the service of the plugin
     * @return the service provided by the application filtered for the provided <code>pluginServiceType</code>
     */
    @NonNull VersionedServiceProvider getServiceProvider(@NonNull VersionedServiceType<?> pluginServiceType);

    /**
     * Plug a serivce to the application. This must
     * not affect the service provider of the application.
     * @param versionedService the service to plug
     */
    void plugService(@NonNull VersionedService versionedService);

    /**
     * Unplug a service from the application.
     * not affect the service provider of the application.
     * @param versionedService the service to unplug
     */
    void unplugService(@NonNull VersionedService versionedService);
}
