package Bastien Aracil.plugins.manager.impl.action;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.impl.PluginRegistry;
import Bastien Aracil.plugins.manager.impl.ServiceTypeProvider;
import Bastien Aracil.plugins.manager.impl.graph.GraphCreator;
import Bastien Aracil.plugins.manager.impl.graph.Node;
import Bastien Aracil.plugins.manager.impl.state.PluginData;

@RequiredArgsConstructor
public class ObsoletePluginFinder {

    public static @NonNull ImmutableSet<Long> search(@NonNull PluginRegistry pluginRegistry) {
        return new ObsoletePluginFinder(pluginRegistry).search();
    }

    private final @NonNull PluginRegistry pluginRegistry;

    private ServiceTypeProvider serviceTypeProvider;


    private @NonNull ImmutableSet<Long> search() {
        this.buildServiceProviderFromInstalledPlugins();

        return this.pluginRegistry.streamPluginData(p -> p.isInPluggedState() && isObsolete(p))
                                  .map(PluginData::getId)
                                  .collect(ImmutableSet.toImmutableSet());
    }

    private boolean isObsolete(PluginData plugin) {
        return serviceTypeProvider.isNewerVersionAvailable(plugin.getProvidedService());
    }

    private void buildServiceProviderFromInstalledPlugins() {
        var graph = GraphCreator.create(this.pluginRegistry.getPluginData(p -> p.isInPluggedState() || p.isInInstalledState()));
        final var pluginList = graph.streamNodes()
                                    .filter(Node::areRequirementFulfilled)
                                    .map(Node::getPluginData)
                                    .collect(ImmutableList.toImmutableList());

        this.serviceTypeProvider = ServiceTypeProvider.create(pluginList, PluginData::getPluginContext);
    }
}
