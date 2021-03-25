package jplugman.manager.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import jplugman.api.ServiceProvider;
import jplugman.api.VersionedServiceClass;
import jplugman.api.VersionedServiceProvider;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginSpecificServiceProvider implements ServiceProvider {

    /**
     * @param pluginRequirements the requirements of the plugins
     * @param versionedServiceProvider the provider of services
     * @return a service provider that will provide only the services required by the plugin
     */
    public static @NonNull ServiceProvider create(
            @NonNull ImmutableSet<VersionedServiceClass<?>> pluginRequirements,
            @NonNull VersionedServiceProvider versionedServiceProvider) {
        return new PluginSpecificServiceProvider(
                pluginRequirements.stream()
                                  .collect(ImmutableMap.toImmutableMap(VersionedServiceClass::getServiceClass, r -> r)),
                versionedServiceProvider
        );
    }

    private final @NonNull ImmutableMap<Class<?>, VersionedServiceClass<?>> pluginRequirements;
    private final @NonNull VersionedServiceProvider versionedServiceProvider;

    @Override
    public @NonNull <T> ImmutableList<T> getAllServices(@NonNull Class<T> serviceClass) {
        return getRequirementFromServiceType(serviceClass)
                .flatMap(versionedServiceProvider::findService)
                .map(ImmutableList::of)
                .orElseGet(ImmutableList::of);
    }

    @SuppressWarnings("unchecked")
    private <T> @NonNull Optional<VersionedServiceClass<T>> getRequirementFromServiceType(@NonNull Class<T> serviceType) {
        final var requirement = pluginRequirements.get(serviceType);
        return Optional.ofNullable((VersionedServiceClass<T>) requirement);
    }
}
