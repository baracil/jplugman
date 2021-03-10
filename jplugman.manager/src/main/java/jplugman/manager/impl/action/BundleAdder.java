package jplugman.manager.impl.action;

import com.google.common.collect.ImmutableList;
import jplugman.manager.Application;
import jplugman.manager.impl.PluginRegistry;
import jplugman.manager.impl.graph.Graph;
import jplugman.manager.impl.graph.GraphCreator;
import jplugman.manager.impl.graph.Node;
import jplugman.manager.impl.state.InstalledState;
import jplugman.manager.impl.state.PluginData;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.nio.file.Path;
import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class BundleAdder {

    public static Consumer<? super Path> adder(@NonNull Application application,
                                               @NonNull PluginRegistry pluginRegistry) {
        return path -> add(application, pluginRegistry, path);
    }

    public static void add(@NonNull Application application, @NonNull PluginRegistry pluginRegistry, @NonNull Path bundleLocation) {
        new BundleAdder(application, pluginRegistry, bundleLocation).add();
    }

    private final @NonNull Application application;

    private final @NonNull PluginRegistry pluginRegistry;

    private final @NonNull Path bundleLocation;

    private ImmutableList<PluginData> pluginDataList;
    private Graph<Node> dependencyGraph;
    private ImmutableList<Node> sortedNodes;

    private void add() {
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
        this.loadBundle();
        this.addLoadedPluginToRegistry();
        this.unloadObsoletePlugins();
        PluginPlugger.plug(pluginRegistry);
        //INVARIANT -> Aucun plugin n'est dans l'état RESOLVED ni FAILED
    }

    private void loadBundle() {
        this.pluginDataList = CompatiblePluginContextCreator.create(application, pluginRegistry, bundleLocation)
                                                            .stream()
                                                            .map(InstalledState::new)
                                                            .map(PluginData::create)
                                                            .collect(ImmutableList.toImmutableList());
    }

    private void addLoadedPluginToRegistry() {
        this.pluginDataList.forEach(pluginRegistry::addPluginData);
    }

    private void unloadObsoletePlugins() {
        final var obsoletePluginIds = ObsoletePluginFinder.search(pluginRegistry);

        // décharger les plugins fournissant les services obsoletes
        final var pluggedPlugins = pluginRegistry.getPluginData(PluginData::isInPluggedState);
        final var graph = GraphCreator.create(pluggedPlugins);

        pluggedPlugins.stream()
                      .map(PluginData::getId)
                      .filter(obsoletePluginIds::contains)
                      .map(graph::getNodeById)
                      .forEach(n -> n.dfs(Node::isPluginInPluggedState, Node::unloadPlugin));
    }

}