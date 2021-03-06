package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.PluginManager;
import Bastien Aracil.plugins.manager.impl.action.BundleAdder;
import Bastien Aracil.plugins.manager.impl.action.BundleRemover;
import Bastien Aracil.plugins.manager.impl.action.PluginPlugger;
import Bastien Aracil.plugins.manager.impl.graph.Graph;
import Bastien Aracil.plugins.manager.impl.graph.GraphCreator;
import Bastien Aracil.plugins.manager.impl.graph.Node;
import Bastien Aracil.plugins.manager.impl.state.PluginData;

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


}
