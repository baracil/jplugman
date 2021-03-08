package Bastien Aracil.plugin.manager.impl.graph;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugin.api.VersionedServiceClass;
import Bastien Aracil.plugin.manager.impl.state.PluginData;

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

        if (dependencies.size() == requirements.size()) {
            return true;
        }

        final var serviceProvider = this.pluginData.getApplicationServiceProvider();

        final Predicate<VersionedServiceClass<?>> providedByPlugins = r -> dependencies.stream().anyMatch(n -> n.getPluginData().getProvidedService().provides(r));
        final Predicate<VersionedServiceClass<?>> providedByMainApplication = serviceProvider::hasService;
        return requirements.stream().allMatch(providedByPlugins.or(providedByMainApplication));
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
