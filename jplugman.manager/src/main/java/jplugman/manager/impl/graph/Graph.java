package jplugman.manager.impl.graph;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Graph<N extends GraphNode<N>> {

    @NonNull
    private final ImmutableMap<Long, N> nodeById;

    public @NonNull Optional<ImmutableList<N>> sort() {
        return TopologicalSorter.sort(nodeById.values());
    }

    public @NonNull N getNodeById(long id) {
        final var node = nodeById.get(id);
        if (node == null) {
            throw new IllegalStateException("No node with id '"+id+"'");
        }
        return node;
    }


    public @NonNull Stream<N> streamNodes() {
        return nodeById.values().stream();
    }
}
