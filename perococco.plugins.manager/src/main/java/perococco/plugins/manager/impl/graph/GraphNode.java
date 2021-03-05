package Bastien Aracil.plugins.manager.impl.graph;

import lombok.NonNull;

import java.util.stream.Stream;

public interface GraphNode<N> {

    @NonNull Stream<N> streamDependants();
}
