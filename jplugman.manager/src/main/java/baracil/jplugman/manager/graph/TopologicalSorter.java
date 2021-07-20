package baracil.jplugman.manager.graph;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.*;
import java.util.function.Function;

public class TopologicalSorter<N extends GraphNode<N>,I> {

    public static <N extends GraphNode<N>,I> @NonNull Optional<ImmutableList<I>> sort(@NonNull Collection<N> nodes, @NonNull Function<? super N, I> idGetter) {
        return new TopologicalSorter<>(nodes,idGetter).execute();
    }

    public static <N extends GraphNode<N>> @NonNull Optional<ImmutableList<N>> sort(@NonNull Collection<N> nodes) {
        return sort(nodes,n -> n);
    }

    private final @NonNull Function<? super N, ? extends I> idGetter;
    private final List<I> sorted;
    private final Set<N> notMarked;
    private final Set<N> permanentMarker = new HashSet<>();
    private final Set<N> temporaryMarker = new HashSet<>();

    private TopologicalSorter(@NonNull Collection<N> nodes, @NonNull Function<? super N, ? extends I> idGetter) {
        this.idGetter = idGetter;
        this.sorted = new ArrayList<>(nodes.size());
        this.notMarked = new HashSet<>(nodes);
    }

    boolean cycleFound = false;

    public @NonNull Optional<ImmutableList<I>> execute() {
        while (!notMarked.isEmpty() && !cycleFound) {
            final var node = notMarked.stream().findFirst().get();
            notMarked.remove(node);
            visit(node);
        }

        if (cycleFound) {
            return Optional.empty();
        } else {
            return Optional.of(ImmutableList.copyOf(sorted).reverse());
        }

    }


    private void visit(N node) {
        if (permanentMarker.contains(node)) {
            return;
        }
        if (temporaryMarker.contains(node)) {
            cycleFound = true;
            return;
        }
        temporaryMarker.add(node);
        node.streamDependants()
            .forEach(this::visit);
        temporaryMarker.remove(node);
        permanentMarker.add(node);
        sorted.add(idGetter.apply(node));
    }

}
