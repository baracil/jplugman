package Bastien Aracil.plugins.manager.impl.action;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.impl.PluginRegistry;
import Bastien Aracil.plugins.manager.impl.graph.Graph;
import Bastien Aracil.plugins.manager.impl.graph.GraphCreator;
import Bastien Aracil.plugins.manager.impl.graph.Node;
import Bastien Aracil.plugins.manager.impl.state.InstalledState;
import Bastien Aracil.plugins.manager.impl.state.PluginData;

import java.nio.file.Path;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BundleAdder {

    public static Consumer<? super Path> adder(@NonNull Application application,
                                               @NonNull PluginRegistry pluginRegistry) {
        return path -> add(application, pluginRegistry, path);
    }

    public static void add(@NonNull Application application, @NonNull PluginRegistry pluginRegistry, @NonNull Path bundleLocation) {
        new BundleAdder(application, pluginRegistry, bundleLocation).add();
    }

    private final @NonNull Application application;

    private final @NonNull PluginRegistry pluginRegistry;

    private final @NonNull Path bundleLocation;

    private ImmutableList<PluginData> pluginDataList;
    private Graph<Node> dependencyGraph;
    private ImmutableList<Node> sortedNodes;

    private void add() {
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.loadBundle();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.addLoadedPluginToRegistry();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.unloadObsoletePlugins();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.buildDependencyGraphForPluggedAndInstalledPlugins();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.topologicallySortDependencyGraph();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        if (this.dependencyGraphContainsACycle()) {
            return;
        }
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.markPluginsWithFulfilledRequirementsAsResolved();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.loadResolvedPlugins();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED
        //uninstall tous les plugins marqués comme FAILED
        this.uninstallFailedPlugins();
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
    }

    private void markPluginsWithFulfilledRequirementsAsResolved() {
        dependencyGraph.streamNodes()
                       .filter(Node::areRequirementFulfilled)
                       .forEach(n -> n.getPluginData().markResolved());
    }

    private boolean dependencyGraphContainsACycle() {
        return this.sortedNodes == null;
    }

    private void topologicallySortDependencyGraph() {
        this.sortedNodes = this.dependencyGraph.sort().orElse(null);
    }

    private void buildDependencyGraphForPluggedAndInstalledPlugins() {
        // construire le graph de dépendances avec les plugins (INSTALLED, PLUGGED)
        final var plugins = pluginRegistry.getPluginData(p -> p.isInInstalledState() || p.isInPluggedState());
        this.dependencyGraph = GraphCreator.create(plugins);
    }

    private void loadBundle() {
        this.pluginDataList = CompatiblePluginContextCreator.create(application, pluginRegistry, bundleLocation)
                                                            .stream()
                                                            .map(InstalledState::new)
                                                            .map(PluginData::create)
                                                            .collect(ImmutableList.toImmutableList());
    }

    private void addLoadedPluginToRegistry() {
        this.pluginDataList.forEach(pluginRegistry::addPluginData);
    }

    private void unloadObsoletePlugins() {
        final var obsoletePluginIds = ObsoletePluginFinder.search(pluginRegistry);

        // décharger les plugins fournissant les services obsoletes
        final var pluggedPlugins = pluginRegistry.getPluginData(PluginData::isInPluggedState);
        final var graph = GraphCreator.create(pluggedPlugins);

        pluggedPlugins.stream()
                      .map(PluginData::getId)
                      .filter(obsoletePluginIds::contains)
                      .map(graph::getNodeById)
                      .forEach(n -> n.dfs(Node::isPluginInPluggedState, Node::unloadPlugin));
    }

    private boolean loadResolvedPlugins() {
        boolean onFailed = false;
        for (Node sortedNode : sortedNodes) {
            sortedNode.loadPlugin();
            if (sortedNode.isPluginInFailedState()) {
                onFailed = true;
                sortedNode.dfsButSkipMe(Node::setToInstalledState);
            }
        }
        return onFailed;
    }

    private void uninstallFailedPlugins() {
        pluginRegistry.getPluginData(PluginData::isInFailedState)
                      .forEach(PluginData::unInstall);

    }


}
