package Bastien Aracil.plugin.manager.impl.graph;

import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface GraphNode<N extends GraphNode<N>> {

    long getNodeId();

    @NonNull Stream<N> streamDependants();

    @NonNull N getThis();

    default void dfs(@NonNull Predicate<? super N> shouldPerform, @NonNull Consumer<? super N> action) {
        if (shouldPerform.test(getThis())) {
            streamDependants().forEach(n -> n.dfs(shouldPerform, action));
            action.accept(getThis());
        }
    }

    default void dfs(@NonNull Consumer<? super N> action) {
        dfs(n -> true, action);
    }

    default void dfsButSkipMe(@NonNull Consumer<? super N> action) {
        final N me = getThis();
        dfs(n -> true, n -> {
            if (n != me) {
                action.accept(n);
            }
        });
    }

}
