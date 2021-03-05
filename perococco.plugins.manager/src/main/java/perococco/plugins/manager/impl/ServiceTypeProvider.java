package Bastien Aracil.plugins.manager.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.VersionedServiceType;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
public class ServiceTypeProvider {

    private final @NonNull Map<Class<?>, Map<Integer, ServiceInfo>> services;

    /**
     * @param requirements the required service type
     * @return an optional containing the id of the plugin
     * that provides the requested requirement if any (for the request major version).
     * An empty optional if no such plugin could be found
     */
    public Optional<Long> findPluginProviding(@NonNull VersionedServiceType<?> requirements) {
        final var requestedService = requirements.getServiceType();
        final var majorVersion = requirements.getVersion().getMajor();

        return services.entrySet()
                       .stream()
                       .filter(e -> requestedService.isAssignableFrom(e.getKey()))
                       .map(e -> e.getValue().get(majorVersion))
                       .filter(Objects::nonNull)
                       .map(ServiceInfo::getPluginId)
                       .findFirst();
    }

    /**
     * @param versionedServiceType
     * @return true if a plugin provides a service with a newer version (for the same major version)
     */
    public boolean isNewerVersionAvailable(@NonNull VersionedServiceType<?> versionedServiceType) {
        return Optional.ofNullable(services.get(versionedServiceType.getServiceType()))
                       .map(v -> v.get(versionedServiceType.getVersion().getMajor()))
                       .map(ServiceInfo::getMinorVersion)
                       .filter(vminor -> vminor > versionedServiceType.getVersion().getMinor())
                       .isPresent();
    }


    @AllArgsConstructor
    public static class ServiceInfo {
        @Getter
        private long pluginId;
        private VersionedServiceType<?> versionedServiceType;

        public @NonNull Class<?> getServiceType() {
            return versionedServiceType.getServiceType();
        }

        public int getMajorVersion() {
            return versionedServiceType.getVersion().getMajor();
        }

        public int getMinorVersion() {
            return versionedServiceType.getVersion().getMinor();
        }

        public static ServiceInfo max(@NonNull ServiceInfo s1, @NonNull ServiceInfo s2) {
            final int comparison = s1.versionedServiceType.compareTo(s2.versionedServiceType);
            return comparison >= 0 ? s1 : s2;
        }

    }


}
