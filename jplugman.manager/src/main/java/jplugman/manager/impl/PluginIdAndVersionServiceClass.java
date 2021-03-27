package jplugman.manager.impl;

import jplugman.api.Version;
import jplugman.manager.impl.state.PluginContext;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Optional;

@AllArgsConstructor
public class PluginIdAndVersionServiceClass {

    public static @NonNull Optional<PluginIdAndVersionServiceClass> createWith(@NonNull PluginContext pluginContext) {
        return pluginContext.getProvidedService()
                            .map(s -> new PluginIdAndVersionServiceClass(pluginContext.getId(),s));
    }

    @Getter
    private final long pluginId;

    @Getter
    private final @NonNull VersionedServiceClass versionedServiceClass;


    public @NonNull ServiceClass getServiceType() {
        return versionedServiceClass.getServiceClass();
    }

    public @NonNull Version getVersion() {
        return versionedServiceClass.getVersion();
    }

    public int getMajorVersion() {
        return versionedServiceClass.getVersion().getMajor();
    }

    public boolean provides(Class<?> requestedService) {
        return requestedService.equals(versionedServiceClass.getServiceClass().getType());
    }

}
