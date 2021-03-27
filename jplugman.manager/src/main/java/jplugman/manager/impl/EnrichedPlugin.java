package jplugman.manager.impl;

import jplugman.api.Plugin;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EnrichedPlugin<T> implements Plugin<T> {

    public static <T> @NonNull Optional<EnrichedPlugin<T>> create(@NonNull Plugin<T> plugin) {
        final var services = ProvidedServiceExtractor.extract(plugin.getExtensionClass());
        return services.map(s -> new EnrichedPlugin<>(plugin,s));
    }

    @Delegate
    private final @NonNull Plugin<T> plugin;

    private final VersionedServiceClass providedService;

    public @NonNull Optional<VersionedServiceClass> getProvidedService() {
        return Optional.ofNullable(providedService);
    }

}
