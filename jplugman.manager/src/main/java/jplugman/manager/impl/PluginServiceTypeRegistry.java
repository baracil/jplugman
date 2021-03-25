package jplugman.manager.impl;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.Table;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.api.VersionedServiceClass;
import jplugman.manager.impl.state.PluginContext;
import jplugman.manager.impl.state.PluginData;

import java.util.Optional;
import java.util.function.Function;

/**
 * Class used to find the plugin providing a specific requirements.
 * From a list of plugins, it uses only the one providing the newest version of each services
 */
@RequiredArgsConstructor
public class PluginServiceTypeRegistry {

    public static @NonNull PluginServiceTypeRegistry create(@NonNull ImmutableCollection<PluginContext> pluginContexts) {
        return create(pluginContexts, p -> p);
    }

    public static <P> @NonNull PluginServiceTypeRegistry create(@NonNull ImmutableCollection<P> values, @NonNull Function<? super P, ? extends PluginContext> pluginContextGetter) {
        return new PluginServiceTypeRegistry(values.stream().map(pluginContextGetter).collect(new ProvidedServiceTypeCollector()));
    }

    private final @NonNull Table<String, Integer, PluginIdAndVersionServiceClass> services;

    /**
     * @param requirement the required service type
     * @return an optional containing the id of the plugin that provides the requested
     * requirement if any (for the request major version). An empty optional if no such plugin could be found
     */
    public Optional<Long> findPluginProviding(@NonNull VersionedServiceClass<?> requirement) {
        final var requestedService = requirement.getServiceClass();
        final var majorVersion = requirement.getVersion().getMajor();

        return services.column(majorVersion)
                       .values()
                       .stream()
                       .filter(p -> p.provides(requestedService))
                       .map(PluginIdAndVersionServiceClass::getPluginId)
                       .findFirst();
    }

    /**
     * @return true if a plugin provides a service with a newer version (for the same major version)
     */
    public boolean isNewerVersionAvailable(@NonNull VersionedServiceClass<?> versionedServiceClass) {
        final var serviceType = versionedServiceClass.getServiceClass();
        final var majorVersion = versionedServiceClass.getVersion().getMajor();
        final var providedServiceType = services.get(serviceType.getName(), majorVersion);

        return providedServiceType != null && providedServiceType.getVersion().compareTo(versionedServiceClass.getVersion())>0;
    }


    /**
     *
     * @param pluginContext the plugin context containing the plugin information
     * @return true if the provided pluginContext provides the last version of the service (for a given major version)
     */
    public boolean isLastVersion(@NonNull PluginContext pluginContext) {
        final var serviceType = pluginContext.getProvidedService().getServiceClass();
        final var majorVersion = pluginContext.getProvidedService().getVersion().getMajor();
        final var providedServiceType = services.get(serviceType.getName(), majorVersion);

        return providedServiceType != null && providedServiceType.getPluginId() == pluginContext.getId();
    }

    public boolean isLastVersion(@NonNull PluginData pluginData) {
        return isLastVersion(pluginData.getPluginContext());
    }

}
