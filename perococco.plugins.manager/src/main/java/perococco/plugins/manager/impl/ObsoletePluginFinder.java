package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ObsoletePluginFinder {

    public static @NonNull ImmutableSet<Long> search(@NonNull PluginRegistry pluginRegistry) {
        return new ObsoletePluginFinder(pluginRegistry).search();
    }

    private final @NonNull PluginRegistry pluginRegistry;

    private ServiceTypeProvider serviceTypeProvider;


    private @NonNull ImmutableSet<Long> search() {
        this.buildServiceProviderFromNotLoadedPlugins();

        return this.pluginRegistry.streamPluginData(PluginData::isPlugged)
                                  .filter(this::isObsolete)
                                  .map(PluginData::getId)
                                  .collect(ImmutableSet.toImmutableSet());
    }

    private boolean isObsolete(PluginData plugin) {
        return serviceTypeProvider.isNewerVersionAvailable(plugin.getProvidedServiceType());
    }

    private void buildServiceProviderFromNotLoadedPlugins() {
        final var pluginList = pluginRegistry.getPluginData(PluginData::isInstalled);
        this.serviceTypeProvider = ServiceTypeProviderFactory.create(pluginList);
    }
}
