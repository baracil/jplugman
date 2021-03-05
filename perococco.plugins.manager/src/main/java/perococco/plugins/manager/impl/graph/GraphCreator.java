package Bastien Aracil.plugins.manager.impl.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.impl.PluginData;
import Bastien Aracil.plugins.manager.impl.PluginRegistry;
import Bastien Aracil.plugins.manager.impl.ServiceTypeProvider;
import Bastien Aracil.plugins.manager.impl.ServiceTypeProviderFactory;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class GraphCreator {

    public static @NonNull Graph create(@NonNull ImmutableList<PluginData> pluginDataInGraph) {
        return new GraphCreator(pluginDataInGraph).create();
    }

    private final ImmutableList<PluginData> pluginDataInGraph;

    private Map<Long, Node> nodes;

    private ServiceTypeProvider serviceTypeProvider;

    private @NonNull Graph create() {
        this.createServiceTypeProvider();
        this.createAllNodes();
        this.forEachPluginLinkDependencies();
        return new Graph(ImmutableMap.copyOf(nodes));
    }

    private void createServiceTypeProvider() {
        serviceTypeProvider = ServiceTypeProviderFactory.create(pluginDataInGraph);
    }

    private void createAllNodes() {
        this.nodes = pluginDataInGraph.stream()
                                      .map(Node::forPlugin)
                                      .collect(Collectors.toMap(Node::getPluginId, Function.identity()));
    }

    private void forEachPluginLinkDependencies() {
        pluginDataInGraph.forEach(this::linkDependenciesForPlugin);
    }

    private void linkDependenciesForPlugin(PluginData p) {
        final var node = this.nodes.get(p.getId());

        p.getPluginRequirements()
         .stream()
         .map(serviceTypeProvider::findPluginProviding)
         .flatMap(Optional::stream)
         .map(id -> nodes.get(id))
         .forEach(node::addDependency);

    }
}
