package Bastien Aracil.plugins.manager.impl.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Graph {

    @NonNull
    private final ImmutableMap<Long, Node> nodeByPluginId;

    public @NonNull Optional<ImmutableList<Long>> sort() {
        return TopologicalSorter.sort(nodeByPluginId.values(), Node::getPluginId);
    }

    public @NonNull Node getNodeById(long id) {
        final var node = nodeByPluginId.get(id);
        if (node == null) {
            throw new IllegalStateException("No node with id '"+id+"'");
        }
        return node;
    }


    public @NonNull Stream<Node> streamNodes() {
        return nodeByPluginId.values().stream();
    }
}
