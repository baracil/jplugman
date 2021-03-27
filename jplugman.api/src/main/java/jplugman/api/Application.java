package jplugman.api;

import lombok.NonNull;

public interface Application {

    /**
     * @param pluginServiceType the type of the service provided by the plugin requesting the application service provider
     * @return the service provided by the application filtered for the provided <code>pluginServiceType</code>
     */
    @NonNull ServiceProvider getServiceProvider(@NonNull Class<?> pluginServiceType);

    /**
     * Plug an extension to the application. This must
     * not affect the service provider of the application.
     * @param extension the service to plug
     */
    void plugExtension(@NonNull Extension extension);

    /**
     * Unplug an extension from the application. This must
     * not affect the service provider of the application.
     * @param extension the service to unplug
     */
    void unplugExtension(@NonNull Extension extension);
}
