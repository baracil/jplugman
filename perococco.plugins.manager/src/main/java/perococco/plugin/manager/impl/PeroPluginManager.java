package Bastien Aracil.plugin.manager.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import Bastien Aracil.plugin.manager.Application;
import Bastien Aracil.plugin.manager.PluginManager;
import Bastien Aracil.plugin.manager.impl.action.BundleAdder;
import Bastien Aracil.plugin.manager.impl.action.BundleRemover;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class PeroPluginManager implements PluginManager {

    private final @NonNull Application application;

    private final PluginRegistry pluginRegistry = new BasicPluginRegistry();

    private final Set<Path> loadedLocations = new HashSet<>();

    @Override
    @Synchronized
    public void addPluginBundle(@NonNull Path pluginBundleLocation) {
        if (loadedLocations.contains(pluginBundleLocation)) {
            return;
        }
        BundleAdder.add(application, pluginRegistry, pluginBundleLocation);
        loadedLocations.add(pluginBundleLocation);
    }


    @Override
    @Synchronized
    public void removePluginBundle(@NonNull Path pluginBundleLocation) {
        BundleRemover.remove(pluginRegistry,pluginBundleLocation);
        loadedLocations.remove(pluginBundleLocation);
    }

    @Override
    @Synchronized
    public void removeAllPluginBundles() {
        loadedLocations.forEach(l -> BundleRemover.remove(pluginRegistry,l));
        loadedLocations.clear();
    }
}
