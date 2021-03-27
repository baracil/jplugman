package jplugman.manager.impl.action;

import com.google.common.collect.ImmutableList;
import jplugman.manager.impl.PluginRegistry;
import jplugman.manager.impl.PluginServiceTypeRegistry;
import jplugman.manager.impl.graph.Graph;
import jplugman.manager.impl.graph.GraphCreator;
import jplugman.manager.impl.graph.Node;
import jplugman.manager.impl.state.PluginData;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginPlugger {

    enum Result {
        PLUG_SUCCEEDED,
        DEPENDENCY_CYCLE_FOUND,
        SOME_LOADING_FAILURE,
    }

    public static @NonNull Result plug(@NonNull PluginRegistry pluginRegistry) {
        return new PluginPlugger(pluginRegistry).add();
    }

    private final @NonNull PluginRegistry pluginRegistry;

    private Graph<Node> dependencyGraph;
    private ImmutableList<Node> sortedNodes;
    private boolean someLoadingFailed = false;

    private Result add() {
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.buildDependencyGraphForPluggedAndInstalledPlugins();
        this.topologicallySortDependencyGraph();
        if (this.dependencyGraphContainsACycle()) {
            return Result.DEPENDENCY_CYCLE_FOUND;
        }
        this.markPluginsWithFulfilledRequirementsAsResolved();
        this.loadResolvedPlugins();
        //TODO in case a plugin failed to load, some plugins that were obsolete might
        //TODO be able to provide the same service as the one from the failed plugin (with same major
        //TODO version). As such, a new pass should be made
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED
        this.uninstallFailedPlugins();

        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        return someLoadingFailed ? Result.SOME_LOADING_FAILURE : Result.PLUG_SUCCEEDED;
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
        //build the dependency graph with plugins that are in installed of plugged state and that are resolved
        final var resolvedPlugins = ResolvedPluginLister.list(pluginRegistry);
        final var serviceTypeProvider = PluginServiceTypeRegistry.create(resolvedPlugins, PluginData::getPluginContext);

        final var newestPlugins = resolvedPlugins.stream()
                                                 .filter(serviceTypeProvider::isLastVersion)
                                                 .collect(ImmutableList.toImmutableList());

        this.dependencyGraph = GraphCreator.create(newestPlugins);
    }


    private void loadResolvedPlugins() {
        someLoadingFailed = false;
        for (Node sortedNode : sortedNodes) {
            sortedNode.loadPlugin();
            if (sortedNode.isPluginInFailedState()) {
                someLoadingFailed = true;
                sortedNode.dfsButSkipMe(Node::setToInstalledState);
            }
        }
    }

    private void uninstallFailedPlugins() {
        pluginRegistry.getPluginData(PluginData::isInFailedState)
                      .forEach(PluginData::unInstall);

    }


}
