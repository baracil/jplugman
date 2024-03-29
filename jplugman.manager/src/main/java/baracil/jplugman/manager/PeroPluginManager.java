package baracil.jplugman.manager;

import baracil.jplugman.manager.action.BundleAdder;
import baracil.jplugman.manager.action.BundleRemover;
import jplugman.api.Application;
import jplugman.manager.PluginManager;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;

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
