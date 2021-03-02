package Bastien Aracil.plugins.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.ServiceLoader;
import java.util.UUID;

public interface Plugin {

    /**
     * Load a plugin in a modular JVM
     * @param pluginLocation the path to the zip containing the plugin
     * @return the result of the loading : a list of plugins and the module layer used to load them)
     */
    static @NonNull PluginLoader.Result load(@NonNull Path pluginLocation) {
        return ServiceLoader.load(PluginLoader.class)
                            .stream()
                            .map(ServiceLoader.Provider::get)
                            .filter(PluginLoader::isModular)
                            .findFirst()
                            .orElseThrow(() -> new IllegalStateException(
                                    "Could not find any modular implementation of " + PluginLoader.class))
                            .load(pluginLocation);
    }

    /**
     * @return the version of the application this plugin has been compiled for
     */
    int getVersionCompatibility();

    /**
     * @return a uniq identifier of the plugin. Different version of the same plugin must have
     * the same id
     */
    @NonNull UUID getId();

    /**
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull ImmutableSet<Requirement> getRequirements();

    /**
     * @return load the service provided by this plugin
     */
    @NonNull VersionedService loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);
}
