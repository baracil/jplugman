package Bastien Aracil.jplugman.manager.action;

import com.google.common.collect.ImmutableSet;
import Bastien Aracil.jplugman.manager.PluginRegistry;
import Bastien Aracil.jplugman.manager.PluginServiceTypeRegistry;
import Bastien Aracil.jplugman.manager.state.PluginData;
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
        final var extensionData = plugin.getExtensionData().orElse(null);
        return extensionData != null && pluginServiceTypeRegistry.isNewerVersionAvailable(extensionData);
    }

    private void buildServiceProviderFromInstalledPlugins() {
        final var resolvedPlugins = ResolvedPluginLister.list(pluginRegistry);

        this.pluginServiceTypeRegistry = PluginServiceTypeRegistry.create(resolvedPlugins, PluginData::getPluginContext);
    }
}
