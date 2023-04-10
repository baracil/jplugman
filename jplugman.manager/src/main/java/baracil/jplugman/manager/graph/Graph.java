package baracil.jplugman.manager.graph;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class Graph<N extends GraphNode<N>> {

    @NonNull
    private final Map<Long, N> nodeById;

    public @NonNull Optional<List<N>> sort() {
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
