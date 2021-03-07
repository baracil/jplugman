package Bastien Aracil.plugins.manager.impl.action;

import com.google.common.collect.ImmutableList;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.impl.PluginRegistry;
import Bastien Aracil.plugins.manager.impl.ServiceTypeProvider;
import Bastien Aracil.plugins.manager.impl.graph.Graph;
import Bastien Aracil.plugins.manager.impl.graph.GraphCreator;
import Bastien Aracil.plugins.manager.impl.graph.Node;
import Bastien Aracil.plugins.manager.impl.state.PluginData;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginPlugger {

    public static void plug(@NonNull PluginRegistry pluginRegistry) {
        new PluginPlugger(pluginRegistry).add();
    }

    private final @NonNull PluginRegistry pluginRegistry;

    private Graph<Node> dependencyGraph;
    private ImmutableList<Node> sortedNodes;

    private void add() {
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.buildDependencyGraphForPluggedAndInstalledPlugins();
        this.topologicallySortDependencyGraph();
        if (this.dependencyGraphContainsACycle()) {
            return;
        }
        this.markPluginsWithFulfilledRequirementsAsResolved();
        this.loadResolvedPlugins();
        //TODO in case a plugin failed to load, some plugin marked obsolete might
        //TODO be able to provide the same service as the one from the failed plugin (with same major
        //TODO version
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED
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

        final var serviceTypeProvider = ServiceTypeProvider.create(plugins, PluginData::getPluginContext);

        final var newestPlugins = plugins.stream()
                                         .filter(serviceTypeProvider::isLastVersion)
                                         .collect(ImmutableList.toImmutableList());

        this.dependencyGraph = GraphCreator.create(newestPlugins);
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
