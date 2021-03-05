package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import lombok.Synchronized;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.PluginManager;
import Bastien Aracil.plugins.manager.impl.graph.GraphCreator;
import Bastien Aracil.plugins.manager.impl.graph.Node;
import Bastien Aracil.plugins.manager.impl.operators.*;

import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class PeroPluginManager implements PluginManager {

    private final @NonNull Application application;

    private final PluginRegistry pluginRegistry = new BasicPluginRegistry();

    private final Set<Path> loadedLocations = new HashSet<>();

    private final Operator unLoader;
    private final Operator loader;

    public PeroPluginManager(@NonNull Application application) {
        this.application = application;
        this.unLoader = new UnLoader(application);
        this.loader = new Loader(application);

    }

    @Override
    @Synchronized
    public void addPluginBundle(@NonNull Path pluginBundleLocation) {
        if (loadedLocations.contains(pluginBundleLocation)) {
            return;
        }
        PluginAdder.addPlugin(pluginRegistry::addPlugin, pluginBundleLocation, application.getVersion());
        loadedLocations.add(pluginBundleLocation);

        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED

        // rechercher les service plugged fournissant une version moins récente que celle
        // fournit par un des plugins ajoutés

        final var obsoletePluginIds = ObsoletePluginFinder.search(pluginRegistry);

        {
            // décharger les plugins fournissant les services obsoletes
            final var pluggedPlugins = pluginRegistry.getPluginData(PluginData::isPlugged);
            final var graph = GraphCreator.create(pluggedPlugins);

            pluggedPlugins.stream()
                          .map(PluginData::getId)
                          .filter(obsoletePluginIds::contains)
                          .forEach(PluginUnLoader.createUnLoader(pluginRegistry, unLoader, graph));
        }


        // construire le graph de dépendances avec les plugins (INSTALLED, PLUGGED)
        final var plugins = pluginRegistry.getPluginData(p -> p.isInstalled() || p.isPlugged());
        final var graph = GraphCreator.create(plugins);

        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED

        final var sortedNodeIds = graph.sort().orElse(null);

        if (sortedNodeIds == null) {
            //cyclic graph
            return;
        }

        // marquer les plugins résolues has RESOLVED
        graph.streamNodes()
             .filter(Node::areRequirementFulfilled)
             .forEach(n -> n.applyOperator(ChangeToResolved.create()));

        // chargement des plugins marqués RESOLVED
        for (long nodeId : sortedNodeIds) {
            final var node = graph.getNodeById(nodeId);
            if (node.isPluginResolved()) {
                node.applyOperator(loader);
            }
            if (node.isPluginInFailure()) {
                // Si le chargement d'un plugin échoue -> marquer ce plugin comme FAILED et
                // marquer tous les plugins dépendants de celui comme INSTALLED
                node.dfs(n -> n.applyOperator(ChangeToInstalled.create()));
            }
        }
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED

        // uninstall tous les plugins marqués comme FAILED
        pluginRegistry.streamPluginData().forEach(p -> p.operate(ChangeFailedToUninstalled.create()));

        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
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
