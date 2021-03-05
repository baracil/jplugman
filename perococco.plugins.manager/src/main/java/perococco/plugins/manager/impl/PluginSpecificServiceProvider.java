package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.VersionedServiceType;
import Bastien Aracil.plugins.api.ServiceProvider;
import Bastien Aracil.plugins.api.VersionedServiceProvider;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PluginSpecificServiceProvider implements ServiceProvider {

    public static @NonNull ServiceProvider create(
            @NonNull ImmutableSet<VersionedServiceType<?>> pluginRequirements,
            @NonNull VersionedServiceProvider versionedServiceProvider) {
        return new PluginSpecificServiceProvider(
                pluginRequirements.stream()
                                  .collect(ImmutableMap.toImmutableMap(VersionedServiceType::getServiceType,
                                                                       r -> r))
                ,
                versionedServiceProvider
        );
    }


    private final @NonNull ImmutableMap<Class<?>, VersionedServiceType<?>> pluginRequirements;

    private final @NonNull VersionedServiceProvider versionedServiceProvider;

    @Override
    public @NonNull <T> Optional<T> findService(@NonNull Class<T> serviceType) {
        return getRequirementFromServiceType(serviceType)
                .flatMap(versionedServiceProvider::findService);
    }

    @SuppressWarnings("unchecked")
    private <T> @NonNull Optional<VersionedServiceType<T>> getRequirementFromServiceType(@NonNull Class<T> serviceType) {
        final var requirement = pluginRequirements.get(serviceType);
        return Optional.ofNullable((VersionedServiceType<T>) requirement);
    }
}
