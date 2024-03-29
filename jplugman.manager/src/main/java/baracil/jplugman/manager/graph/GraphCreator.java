package baracil.jplugman.manager.graph;

import baracil.jplugman.manager.PluginServiceTypeRegistry;
import baracil.jplugman.manager.state.PluginData;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphCreator {

    public static @NonNull Graph<Node> create(@NonNull List<PluginData> pluginDataInGraph) {
        return new GraphCreator(pluginDataInGraph).create();
    }

    private final List<PluginData> pluginDataInGraph;

    private Map<Long, Node> nodes;

    private PluginServiceTypeRegistry pluginServiceTypeRegistry;

    private @NonNull Graph<Node> create() {
        this.createServiceTypeProvider();
        this.createAllNodes();
        this.forEachPluginLinkDependencies();
        return new Graph<>(nodes);
    }

    private void createServiceTypeProvider() {
        pluginServiceTypeRegistry = PluginServiceTypeRegistry.create(pluginDataInGraph, PluginData::getPluginContext);
    }

    private void createAllNodes() {
        this.nodes = pluginDataInGraph.stream()
                                      .map(Node::forPlugin)
                                      .collect(Collectors.toMap(Node::getNodeId, Function.identity()));
    }

    private void forEachPluginLinkDependencies() {
        pluginDataInGraph.forEach(this::linkDependenciesForPlugin);
    }

    private void linkDependenciesForPlugin(PluginData p) {
        final var node = this.nodes.get(p.getId());

        p.getPluginRequirements()
         .stream()
         .map(pluginServiceTypeRegistry::findPluginProviding)
         .flatMap(Optional::stream)
         .map(id -> nodes.get(id))
         .forEach(node::addDependency);
    }
}
