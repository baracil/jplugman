package baracil.jplugman.manager.graph;

import baracil.jplugman.manager.state.PluginData;
import jplugman.api.Requirement;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Node implements GraphNode<Node> {


    public static @NonNull Node forPlugin(@NonNull PluginData pluginData) {
        return new Node(pluginData);
    }

    @Getter
    private final PluginData pluginData;

    /**
     * List of the nodes required to load the plugin
     */
    private final List<Node> dependencies = new ArrayList<>();

    private final List<Node> dependants = new ArrayList<>();

    public @NonNull Stream<Node> streamDependants() {
        return dependants.stream();
    }

    @Override
    public @NonNull Node getThis() {
        return this;
    }

    public long getNodeId() {
        return pluginData.getId();
    }

    public boolean areRequirementFulfilled() {
        final var requirements = this.pluginData.getPluginRequirements();
        final var serviceProvider = this.pluginData.getApplicationServiceProvider();

        final Predicate<Requirement<?>> providedByPlugins = this::anyDependencyProvides;
        final Predicate<Requirement<?>> providedByMainApplication = serviceProvider::hasExtensionPoint;

        return requirements.stream().allMatch(providedByPlugins.or(providedByMainApplication));
    }

    private boolean anyDependencyProvides(@NonNull Requirement<?> requirement) {
        return dependencies.stream().anyMatch(node -> node.isPluginProvides(requirement));
    }

    private boolean isPluginProvides(@NonNull Requirement<?> requirement) {
        return pluginData.pluginProvides(requirement);
    }

    public void addDependency(Node dependency) {
        dependencies.add(dependency);
        dependency.dependants.add(this);
    }

    public void unloadPlugin() {
        this.pluginData.unload();
    }

    public void loadPlugin() {
        this.pluginData.load();
    }

    public void setToInstalledState() {
        this.pluginData.setToInstalledState();
    }

    public boolean isPluginInPluggedState() {
        return pluginData.isInPluggedState();
    }

    public boolean isPluginInFailedState() {
        return pluginData.isInFailedState();
    }

}
