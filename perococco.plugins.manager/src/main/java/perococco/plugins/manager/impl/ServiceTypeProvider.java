package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Table;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.VersionedServiceType;
import Bastien Aracil.plugins.manager.impl.state.PluginContext;
import Bastien Aracil.plugins.manager.impl.state.PluginData;

import java.util.Optional;
import java.util.function.Function;

@RequiredArgsConstructor
public class ServiceTypeProvider {

    public static @NonNull ServiceTypeProvider create(@NonNull ImmutableCollection<PluginContext> pluginContexts) {
        return create(pluginContexts, p -> p);
    }
    public static <P> @NonNull ServiceTypeProvider create(@NonNull ImmutableCollection<P> values, @NonNull Function<? super P, ? extends PluginContext> pluginContextGetter) {
        return new ServiceTypeProvider(values.stream().map(pluginContextGetter).collect(new ProvidedServiceTypeCollector()));
    }

    private final @NonNull Table<Class<?>, Integer, ProvidedServiceType> services;

    /**
     * @param requirements the required service type
     * @return an optional containing the id of the plugin that provides the requested
     * requirement if any (for the request major version). An empty optional if no such plugin could be found
     */
    public Optional<Long> findPluginProviding(@NonNull VersionedServiceType<?> requirements) {
        final var requestedService = requirements.getServiceType();
        final var majorVersion = requirements.getVersion().getMajor();

        return services.column(majorVersion)
                       .values()
                       .stream()
                       .filter(p -> p.provides(requestedService))
                       .map(ProvidedServiceType::getPluginId)
                       .findFirst();
    }

    /**
     * @return true if a plugin provides a service with a newer version (for the same major version)
     */
    public boolean isNewerVersionAvailable(@NonNull VersionedServiceType<?> versionedServiceType) {
        final var serviceType = versionedServiceType.getServiceType();
        final var majorVersion = versionedServiceType.getVersion().getMajor();
        final var providedServiceType = services.get(serviceType, majorVersion);

        return providedServiceType != null && providedServiceType.getVersion().compareTo(versionedServiceType.getVersion())>0;
    }


    public boolean isLastVersion(@NonNull PluginData p) {
        final var serviceType = p.getProvidedService().getServiceType();
        final var majorVersion = p.getProvidedService().getVersion().getMajor();
        final var providedServiceType = services.get(serviceType, majorVersion);

        return providedServiceType != null && providedServiceType.getPluginId() == p.getId();
    }
}
