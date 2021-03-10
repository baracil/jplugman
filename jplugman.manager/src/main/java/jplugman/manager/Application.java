package jplugman.manager;

import jplugman.api.*;
import lombok.NonNull;

public interface Application {

    /**
     * The application version the plugins will be plugged to. This
     * is checked against {@link Plugin#getApplicationVersion()}.
     */
    @NonNull Version getVersion();

    /**
     * @param pluginServiceType the type of the service provided by the plugin requesting the application service provider
     * @return the service provided by the application filtered for the provided <code>pluginServiceType</code>
     */
    @NonNull VersionedServiceProvider getServiceProvider(@NonNull VersionedServiceClass<?> pluginServiceType);

    /**
     * Plug a service to the application. This must
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
