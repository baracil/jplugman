package Bastien Aracil.jplugman.manager;

import com.google.common.collect.ImmutableCollection;
import jplugman.api.ExtensionData;
import jplugman.api.Requirement;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.jplugman.manager.state.PluginContext;
import Bastien Aracil.jplugman.manager.state.PluginData;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

/**
 * Class used to find the plugin providing a specific requirements.
 * From a list of plugins, it uses only the one providing the newest version of each services
 */
@RequiredArgsConstructor
public class PluginServiceTypeRegistry {

    public static <P> @NonNull PluginServiceTypeRegistry create(@NonNull ImmutableCollection<P> values, @NonNull Function<? super P, ? extends PluginContext> pluginContextGetter) {
        return new PluginServiceTypeRegistry(values.stream().map(pluginContextGetter).collect(new ProvidedServiceTypeCollector()));
    }

    private final @NonNull Map<Class<?>, PluginIdAndExtensionData> services;

    /**
     * @param requirement the required service type
     * @return an optional containing the id of the plugin that provides the requested
     * requirement if any (for the request major version). An empty optional if no such plugin could be found
     */
    public Optional<Long> findPluginProviding(@NonNull Requirement<?> requirement) {
        final var requestedService = requirement.getServiceType();



        return Optional.ofNullable(services.get(requestedService)).map(PluginIdAndExtensionData::getPluginId);
    }

    /**
     * @return true if a plugin provides a service with a newer version (for the same major version)
     */
    public boolean isNewerVersionAvailable(@NonNull ExtensionData<?> extensionData) {
        final var serviceType = extensionData.getExtensionPointType();

        final var pluginIdAndExtensionData = services.get(serviceType);

        return pluginIdAndExtensionData != null && pluginIdAndExtensionData.getVersion().compareTo(extensionData.getVersion())>0;
    }


    /**
     * @return true if versionedServiceClass provides the last version of the service (for a given major version)
     */
    public boolean isLastVersion(@NonNull PluginData pluginData) {
        final var extensionData = pluginData.getExtensionData().orElse(null);
        if (extensionData == null) {
            return true;
        }
        final var serviceType = extensionData.getExtensionPointType();
        final var providedServiceType = services.get(serviceType);

        return providedServiceType != null && providedServiceType.getPluginId() == pluginData.getId();
    }

}
