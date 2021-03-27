package Bastien Aracil.jplugman.manager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginSpecificServiceProvider implements ServiceProvider {

    /**
     * @param pluginRequirements       the requirements of the plugins
     * @param versionedServiceProvider the provider of services
     * @return a service provider that will provide only the services required by the plugin
     */
    public static @NonNull ServiceProvider create(
            @NonNull ImmutableSet<Requirement<?>> pluginRequirements,
            @NonNull VersionedServiceProvider versionedServiceProvider) {
        return new PluginSpecificServiceProvider(
                pluginRequirements.stream()
                                  .collect(ImmutableMap.toImmutableMap(Requirement::getServiceType, r -> r)),
                versionedServiceProvider
        );
    }

    private final @NonNull ImmutableMap<Class<?>, Requirement<?>> pluginRequirements;
    private final @NonNull VersionedServiceProvider versionedServiceProvider;

    @Override
    public <T> @NonNull ImmutableList<T> getAllServices(@NonNull Class<T> serviceClass) {
        return getRequirementFromServiceType(serviceClass)
                .stream()
                .flatMap(versionedServiceProvider::findService)
                .collect(ImmutableList.toImmutableList());
    }

    @SuppressWarnings("unchecked")
    private <T> @NonNull Optional<Requirement<T>> getRequirementFromServiceType(@NonNull Class<T> serviceType) {
        final var requirement = pluginRequirements.get(serviceType);
        return Optional.ofNullable((Requirement<T>) requirement);
    }
}
