package jplugman.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface Plugin {

    /**
     * Load a plugin bundle in a modular JVM
     * @param pluginLocation the path to the zip containing the plugin
     * @return the result of the loading : a list of plugins and the module layer used to load them
     */
    static @NonNull PluginLoader.Result loadBundle(@NonNull Path pluginLocation) {
        return ServiceLoader.load(PluginLoader.class)
                            .stream()
                            .map(ServiceLoader.Provider::get)
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException(
                                    "Could not find any implementation of " + PluginLoader.class))
                            .load(pluginLocation);
    }

    /**
     * @return the version of the application this plugin has been compiled for
     */
    @NonNull Version getApplicationVersion();

    /**
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull ImmutableSet<VersionedServiceClass<?>> getRequirements();

    /**
     * @return the class and the version of the service this plugin provides
     */
    @NonNull VersionedServiceClass<?> getProvidedServiceClass();

    /**
     * @return load the service provided by this plugin
     */
    @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);
}
