package Bastien Aracil.plugins.manager;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.PeroPluginManager;

import java.nio.file.Path;

public interface PluginManager {

    static @NonNull PluginManager create(@NonNull Application application) {
        return new PeroPluginManager(application);
    }

    /**
     * Warn this manager that a new plugin is available.
     * @param pluginLocation the location of the plugin to add
     */
    void addPlugin(@NonNull Path pluginLocation);

    /**
     * Warn this manager that a plugin has been removed
     * @param pluginLocation the location of the plugin that has been remove
     */
    void removePlugin(@NonNull Path pluginLocation);

}
