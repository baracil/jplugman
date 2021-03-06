package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.PluginManager;
import Bastien Aracil.plugins.manager.impl.action.BundleAdder;

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
        BundleAdder.add(application,pluginRegistry,pluginBundleLocation);
        loadedLocations.add(pluginBundleLocation);
    }


    @Override
    @Synchronized
    public void removePluginBundle(@NonNull Path pluginBundleLocation) {

        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED

        //Marquer tous les plugins INSTALLED as UNINSTALLED
        //Décharger les plugins fournis par le bundle (en respectant le tri topologie)
        //Marquer les plugins déchargés non fournis par le bundle comme INSTALLED
        //Marquer les plugins déchargés fournis par le bundle comme UNINSTALLED

        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED

    }


}
