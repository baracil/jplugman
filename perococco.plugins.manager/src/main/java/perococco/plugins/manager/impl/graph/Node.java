package Bastien Aracil.plugins.manager.impl.graph;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.impl.Operator;
import Bastien Aracil.plugins.manager.impl.PluginData;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Node implements GraphNode<Node> {


    public static @NonNull Node forPlugin(@NonNull PluginData pluginData) {
        return new Node(pluginData);
    }

    private final PluginData pluginData;

    /**
     * List of the nodes required to load the plugin
     */
    private final List<Node> dependencies = new ArrayList<>();

    private final List<Node> dependants = new ArrayList<>();

    public @NonNull Stream<Node> streamDependants() {
        return dependants.stream();
    }

    public long getPluginId() {
        return pluginData.getId();
    }

    public boolean areRequirementFulfilled() {
        return pluginData.getPlugin().getRequirements().size() == dependencies.size();
    }

    public void applyOperator(@NonNull Operator operator) {
        this.pluginData.operate(operator);
    }

    public void addDependency(Node dependency) {
        dependencies.add(dependency);
        dependency.dependants.add(this);
    }

    public boolean isPluginResolved() {
        return pluginData.isResolved();
    }

    public boolean isPluginInFailure() {
        return pluginData.isFailed();
    }

    public void dfs(@NonNull Consumer<Node> action) {
        dependants.forEach(n -> n.dfs(action));
        action.accept(this);
    }


}
