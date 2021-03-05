package Bastien Aracil.plugins.manager.impl;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.impl.graph.Graph;
import Bastien Aracil.plugins.manager.impl.graph.Node;

import java.util.function.Consumer;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginUnLoader {

    public static Consumer<Long> createUnLoader(@NonNull PluginRegistry pluginRegistry,
                                                @NonNull Operator unLoader,
                                                @NonNull Graph dependencyGraph) {
        return id -> unload(pluginRegistry,unLoader,dependencyGraph,id);
    }

    public static void unload(@NonNull PluginRegistry pluginRegistry,
                              @NonNull Operator unLoader,
                              @NonNull Graph dependencyGraph,
                              @NonNull Long idOfThePluginToUnload) {
        new PluginUnLoader(pluginRegistry, unLoader, dependencyGraph, idOfThePluginToUnload).unload();
    }


    private final @NonNull PluginRegistry pluginRegistry;
    private final @NonNull Operator unLoader;
    private final @NonNull Graph dependencyGraph;
    private final @NonNull Long idOfPluginToUnload;

    private void unload() {
        final var node = dependencyGraph.getNodeById(idOfPluginToUnload);
        this.unloadPlugin(node);
    }


    private void unloadPlugin(Node node) {
        node.streamDependants().forEach(this::unloadPlugin);
        final var pluginData = pluginRegistry.getPluginData(node.getPluginId());
        pluginData.operate(unLoader);
    }

}
