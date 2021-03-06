package Bastien Aracil.plugins.manager.impl.graph;

import lombok.NonNull;

import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface GraphNode<N extends GraphNode<N>> {

    long getNodeId();

    @NonNull Stream<N> streamDependants();

    @NonNull N getThis();

    default void dfs(@NonNull Predicate<? super N> shouldSkip, @NonNull Consumer<? super N> action) {
        if (!shouldSkip.test(getThis())) {
            streamDependants().forEach(n -> n.dfs(shouldSkip, action));
            action.accept(getThis());
        }
    }

    default void dfs(@NonNull Consumer<? super N> action) {
        dfs(n -> false, action);
    }

    default void dfsButSkipMe(@NonNull Consumer<? super N> action) {
        final N me = getThis();
        dfs(n -> false, n -> {
            if (n != me) {
                action.accept(n);
            }
        });
    }
}
