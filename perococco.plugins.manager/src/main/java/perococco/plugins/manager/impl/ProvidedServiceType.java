package Bastien Aracil.plugins.manager.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.api.VersionedServiceType;
import Bastien Aracil.plugins.manager.impl.state.PluginContext;

import java.util.Comparator;

@AllArgsConstructor
public class ProvidedServiceType {

    public static final Comparator<ProvidedServiceType> ID_THEN_VERSION = Comparator.comparing(ProvidedServiceType::getPluginId)
                                                                                    .thenComparing(ProvidedServiceType::getVersionedServiceType);

    public static ProvidedServiceType create(@NonNull PluginContext pluginContext) {
        return new ProvidedServiceType(pluginContext.getId(), pluginContext.getProvidedService());
    }

    @Getter
    private final long pluginId;

    @Getter
    private final @NonNull VersionedServiceType<?> versionedServiceType;

    public @NonNull Class<?> getServiceType() {
        return versionedServiceType.getServiceType();
    }

    public @NonNull Version getVersion() {
        return versionedServiceType.getVersion();
    }

    public int getMajorVersion() {
        return versionedServiceType.getVersion().getMajor();
    }

    public int getMinorVersion() {
        return versionedServiceType.getVersion().getMinor();
    }

    public boolean provides(Class<?> requestedService) {
        return requestedService.equals(versionedServiceType.getServiceType());
    }
}
