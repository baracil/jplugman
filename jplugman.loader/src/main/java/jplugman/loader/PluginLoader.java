package jplugman.loader;

import com.google.common.collect.ImmutableList;
import jplugman.api.Plugin;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface PluginLoader {

    /**
     * Load a plugin from a zip file
     * @param zipFileLocation the path to the zip file to load
     * @return the result of the loading
     */
    @NonNull Result load(@NonNull Path zipFileLocation);

    @RequiredArgsConstructor
    @Getter
    class Result {
        /**
         * The module layer used to load the plugins
         */
        private final @NonNull ModuleLayer pluginLayer;

        /**
         * The loaded plugins
         */
        private final @NonNull ImmutableList<Plugin<?>> plugins;

    }

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

}
