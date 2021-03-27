package jplugman.manager.impl.action;

import com.google.common.collect.ImmutableSet;
import jplugman.manager.impl.PluginRegistry;
import jplugman.manager.impl.PluginServiceTypeRegistry;
import jplugman.manager.impl.state.PluginData;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

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
        final var versionedServiceClass = plugin.getProvidedService().orElse(null);
        return versionedServiceClass != null && pluginServiceTypeRegistry.isNewerVersionAvailable(versionedServiceClass);
    }

    private void buildServiceProviderFromInstalledPlugins() {
        final var resolvedPlugins = ResolvedPluginLister.list(pluginRegistry);

        this.pluginServiceTypeRegistry = PluginServiceTypeRegistry.create(resolvedPlugins, PluginData::getPluginContext);
    }
}
