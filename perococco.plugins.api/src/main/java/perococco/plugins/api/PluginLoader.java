package Bastien Aracil.plugins.api;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;

public interface PluginLoader {

    @NonNull Result load(@NonNull Path location);

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
        private final @NonNull ImmutableList<Plugin> plugins;

        public @NonNull VersionedService loadService(int pluginIndex, ServiceProvider serviceProvider) {
            return plugins.get(pluginIndex).loadService(pluginLayer, serviceProvider);
        }
    }
}
