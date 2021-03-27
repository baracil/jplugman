package jplugman.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface Plugin<T> {

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
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull ImmutableSet<Requirement<?>> getRequirements();

    /**
     * @return the class of the extension this plugin provides
     */
    @NonNull Class<T> getExtensionClass();

    /**
     * @return load the extension provided by this plugin
     */
    @NonNull T loadExtension(@NonNull ModuleLayer pluginLayer, @NonNull ServiceRegistry serviceRegistry);
}
