package baracil.jplugman.manager.action;

import baracil.jplugman.manager.PluginRegistry;
import baracil.jplugman.manager.graph.Graph;
import baracil.jplugman.manager.graph.GraphCreator;
import baracil.jplugman.manager.graph.Node;
import baracil.jplugman.manager.state.PluginData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ResolvedPluginLister {

    public static @NonNull List<PluginData> list(@NonNull PluginRegistry pluginRegistry) {
        return new ResolvedPluginLister(pluginRegistry).list();
    }

    private final @NonNull PluginRegistry pluginRegistry;

    private List<PluginData> installedOrPluggedPlugins;
    private Graph<Node> dependencyGraph;
    private List<PluginData> resolvedPlugins;

    private @NonNull List<PluginData> list() {
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
            .collect(Collectors.toList());
    }
}
