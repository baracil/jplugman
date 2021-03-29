package jplugman.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface Application {

    /**
     * @param pluginExtensionType the type of the service provided by the plugin requesting the application services.
     * @return the services provided by the application filtered or prepared for the provided <code>pluginServiceType</code>
     */
    @NonNull ImmutableSet<VersionedService> getApplicationServices(@NonNull Class<?> pluginExtensionType);

    /**
     * Plug a service to the application. This must
     * not affect the service provider of the application.
     * @param versionedService the data (the instance and the version) of the extension to plug
     */
    void plugService(@NonNull PluginService versionedService);

    /**
     * Unplug a service from the application. This must
     * not affect the service provider of the application.
     * @param versionedService the data of the extension to unplug
     */
    void unplugService(@NonNull PluginService versionedService);
}
