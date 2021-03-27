package jplugman.manager.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import jplugman.api.Requirement;
import jplugman.api.ServiceRegistry;
import jplugman.api.ServiceProvider;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginSpecificServiceRegistry implements ServiceRegistry {

    /**
     * @param pluginRequirements       the requirements of the plugins
     * @param serviceProvider the provider of services
     * @return a service provider that will provide only the services required by the plugin
     */
    public static @NonNull ServiceRegistry create(
            @NonNull ImmutableSet<Requirement<?>> pluginRequirements,
            @NonNull ServiceProvider serviceProvider) {
        return new PluginSpecificServiceRegistry(
                pluginRequirements.stream()
                                  .collect(ImmutableMap.toImmutableMap(Requirement::getServiceType, r -> r)),
                serviceProvider
        );
    }

    private final @NonNull ImmutableMap<Class<?>, Requirement<?>> pluginRequirements;
    private final @NonNull ServiceProvider serviceProvider;

    @Override
    public <T> @NonNull ImmutableList<T> getAllServices(@NonNull Class<T> serviceClass) {
        return getRequirementFromServiceType(serviceClass)
                .stream()
                .flatMap(serviceProvider::findService)
                .collect(ImmutableList.toImmutableList());
    }

    @SuppressWarnings("unchecked")
    private <T> @NonNull Optional<Requirement<T>> getRequirementFromServiceType(@NonNull Class<T> serviceType) {
        final var requirement = pluginRequirements.get(serviceType);
        return Optional.ofNullable((Requirement<T>) requirement);
    }
}
