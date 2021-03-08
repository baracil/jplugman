package Bastien Aracil.plugin.manager.impl.action;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugin.manager.impl.PluginRegistry;
import Bastien Aracil.plugin.manager.impl.PluginServiceTypeRegistry;
import Bastien Aracil.plugin.manager.impl.graph.GraphCreator;
import Bastien Aracil.plugin.manager.impl.graph.Node;
import Bastien Aracil.plugin.manager.impl.state.PluginData;

@RequiredArgsConstructor
public class ObsoletePluginFinder {

    public static @NonNull ImmutableSet<Long> search(@NonNull PluginRegistry pluginRegistry) {
        return new ObsoletePluginFinder(pluginRegistry).search();
    }

    private final @NonNull PluginRegistry pluginRegistry;

    private PluginServiceTypeRegistry pluginServiceTypeRegistry;


    private @NonNull ImmutableSet<Long> search() {
        this.buildServiceProviderFromInstalledPlugins();

        return this.pluginRegistry.streamPluginData(p -> p.isInPluggedState() && isObsolete(p))
                                  .map(PluginData::getId)
                                  .collect(ImmutableSet.toImmutableSet());
    }

    private boolean isObsolete(PluginData plugin) {
        return pluginServiceTypeRegistry.isNewerVersionAvailable(plugin.getProvidedService());
    }

    private void buildServiceProviderFromInstalledPlugins() {
        var graph = GraphCreator.create(this.pluginRegistry.getPluginData(p -> p.isInPluggedState() || p.isInInstalledState()));
        final var pluginList = graph.streamNodes()
                                    .filter(Node::areRequirementFulfilled)
                                    .map(Node::getPluginData)
                                    .collect(ImmutableList.toImmutableList());

        this.pluginServiceTypeRegistry = PluginServiceTypeRegistry.create(pluginList, PluginData::getPluginContext);
    }
}
