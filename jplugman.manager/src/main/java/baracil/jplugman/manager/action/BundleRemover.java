package baracil.jplugman.manager.action;

import baracil.jplugman.manager.PluginRegistry;
import baracil.jplugman.manager.graph.Graph;
import baracil.jplugman.manager.graph.GraphCreator;
import baracil.jplugman.manager.graph.Node;
import baracil.jplugman.manager.state.PluginData;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BundleRemover {

    public static void remove(@NonNull PluginRegistry pluginRegistry, @NonNull Path bundleLocation) {
        new BundleRemover(pluginRegistry, bundleLocation).remove();
    }


    private final @NonNull PluginRegistry pluginRegistry;

    private final @NonNull Path bundleLocation;

    private Graph<Node> dependencyGraph;
    private List<PluginData> pluginsToRemove;

    private void remove() {
        this.buildDependencyGraphOfPluggedPlugins();
        this.retrievePluginsToRemove();
        this.unloadPluginsToRemove();
        this.uninstallAndRemovePlugins();

        PluginPlugger.plug(pluginRegistry);
    }

    private void buildDependencyGraphOfPluggedPlugins() {
        final var pluggedPlugins = pluginRegistry.getPluginData(PluginData::isInPluggedState);
        this.dependencyGraph = GraphCreator.create(pluggedPlugins);
    }

    private void retrievePluginsToRemove() {
        this.pluginsToRemove = pluginRegistry.getPluginData(p -> p.isFromBundle(bundleLocation));
    }

    private void unloadPluginsToRemove() {
        pluginsToRemove.stream()
                       .filter(PluginData::isInPluggedState)
                       .map(PluginData::getId)
                       .map(dependencyGraph::getNodeById)
                       .forEach(n -> n.dfs(Node::unloadPlugin));
    }

    private void uninstallAndRemovePlugins() {
        pluginsToRemove.forEach(p -> {
            p.unInstall();
            pluginRegistry.removePluginData(p.getId());
        });
    }



}
