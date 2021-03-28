package Bastien Aracil.jplugman.manager.action;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.jplugman.manager.PluginRegistry;
import Bastien Aracil.jplugman.manager.graph.Graph;
import Bastien Aracil.jplugman.manager.graph.GraphCreator;
import Bastien Aracil.jplugman.manager.graph.Node;
import Bastien Aracil.jplugman.manager.state.PluginData;

@RequiredArgsConstructor
public class ResolvedPluginLister {

    public static @NonNull ImmutableList<PluginData> list(@NonNull PluginRegistry pluginRegistry) {
        return new ResolvedPluginLister(pluginRegistry).list();
    }

    private final @NonNull PluginRegistry pluginRegistry;

    private ImmutableList<PluginData> installedOrPluggedPlugins;
    private Graph<Node> dependencyGraph;
    private ImmutableList<PluginData> resolvedPlugins;

    private @NonNull ImmutableList<PluginData> list() {
        this.getInstalledOrPluggedPlugin();
        this.buildDependencyGraph();
        this.extractResolvedPluginFromGraph();
        return resolvedPlugins;
    }

    private void getInstalledOrPluggedPlugin() {
        this.installedOrPluggedPlugins = pluginRegistry.getPluginData(
                p -> p.isInPluggedState() || p.isInInstalledState());
    }

    private void buildDependencyGraph() {
        this.dependencyGraph = GraphCreator.create(installedOrPluggedPlugins);
    }

    private void extractResolvedPluginFromGraph() {
        this.resolvedPlugins = dependencyGraph.streamNodes()
                                              .filter(Node::areRequirementFulfilled)
                                              .map(Node::getPluginData)
                                              .collect(ImmutableList.toImmutableList());
    }
}
