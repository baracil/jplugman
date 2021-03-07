package Bastien Aracil.plugins.manager.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import Bastien Aracil.plugins.api.Version;
import Bastien Aracil.plugins.api.VersionedServiceClass;
import Bastien Aracil.plugins.manager.impl.state.PluginContext;

@AllArgsConstructor
public class PluginIdAndVersionServiceClass {

    public static PluginIdAndVersionServiceClass createWith(@NonNull PluginContext pluginContext) {
        return new PluginIdAndVersionServiceClass(pluginContext.getId(), pluginContext.getProvidedService());
    }

    @Getter
    private final long pluginId;

    @Getter
    private final @NonNull VersionedServiceClass<?> versionedServiceClass;


    public @NonNull Class<?> getServiceType() {
        return versionedServiceClass.getServiceClass();
    }

    public @NonNull Version getVersion() {
        return versionedServiceClass.getVersion();
    }

    public int getMajorVersion() {
        return versionedServiceClass.getMajorVersion();
    }

    public boolean provides(Class<?> requestedService) {
        return requestedService.equals(versionedServiceClass.getServiceClass());
    }

}
