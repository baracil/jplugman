package Bastien Aracil.jplugman.manager;

import com.google.common.collect.ImmutableSet;
import jplugman.api.*;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EnrichedPlugin<T> {

    public static <T> @NonNull EnrichedPlugin<T> create(@NonNull Plugin<T> plugin) {
        final var extensionData = ProvidedServiceExtractor.extract(plugin.getServiceClass());
        return extensionData.map(s -> new EnrichedPlugin<>(plugin,s))
                            .orElseGet(() -> new EnrichedPlugin<>(plugin,null));
    }

    private final @NonNull Plugin<T> plugin;

    private final ExtensionData<? super T> extensionData;

    public @NonNull PluginService<T> load(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider) {
        final var service = this.plugin.loadService(pluginLayer,serviceProvider);
        return new PluginService<>(service, extensionData);
    }

    public @NonNull Optional<ExtensionData<? super T>> getExtensionData() {
        return Optional.ofNullable(extensionData);
    }

    public @NonNull Class<T> getServiceClass() {
        return plugin.getServiceClass();
    }

    public ImmutableSet<Requirement<?>> getRequirements() {
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
