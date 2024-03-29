package baracil.jplugman.manager;

import jplugman.api.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EnrichedPlugin {

    public static @NonNull EnrichedPlugin create(@NonNull Plugin plugin) {
        final var extensionData = ProvidedServiceExtractor.extract(plugin.getServiceClass());
        return extensionData.map(s -> new EnrichedPlugin(plugin, s))
                            .orElseGet(() -> new EnrichedPlugin(plugin, null));
    }

    private final @NonNull Plugin plugin;

    private final ExtensionData extensionData;

    public @NonNull PluginService load(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var service = this.plugin.loadService(pluginLayer,serviceProvider);
        return new PluginService(service, extensionData);
    }

    public @NonNull Optional<ExtensionData> getExtensionData() {
        return Optional.ofNullable(extensionData);
    }

    public @NonNull Class<?> getServiceClass() {
        return plugin.getServiceClass();
    }

    public Set<Requirement<?>> getRequirements() {
        return plugin.getRequirements();
    }

    @Override
    public String toString() {
        if (extensionData == null) {
            return "Plugin{" + plugin.getServiceClass() + "}";
        } else {
            return "Plugin{" + plugin.getServiceClass() + " "+extensionData.getExtensionPointType()+" "+extensionData.getVersion()+"}";
        }
    }
}
