package Bastien Aracil.plugins.manager;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.PeroPluginManager;

import java.nio.file.Path;
import java.util.ServiceLoader;

public interface PluginManager {

    static @NonNull PluginManager create(@NonNull Application application) {
        return ServiceLoader.load(Factory.class)
                            .findFirst()
                            .orElse(PeroPluginManager::new)
                            .create(application);
    }

    /**
     * Warn this manager that a new plugin is available.
     *
     * @param pluginLocation the location of the plugin to add
     */
    void addPluginBundle(@NonNull Path pluginLocation);

    /**
     * Warn this manager that a plugin has been removed
     *
     * @param pluginLocation the location of the plugin that has been remove
     */
    void removePluginBundle(@NonNull Path pluginLocation);


    interface Factory {
        @NonNull PluginManager create(@NonNull Application application);
    }
}
