package jplugman.test.test;

import jplugman.api.PluginService;
import jplugman.api.Version;
import lombok.NonNull;
import lombok.Value;

@Value
public class ServiceTypeWithVersion {

    public static ServiceTypeWithVersion create(@NonNull Class<?> serviceType, @NonNull String version) {
        return new ServiceTypeWithVersion(serviceType, Version.with(version));
    }

    @NonNull Class<?> serviceType;

    @NonNull Version version;

    public boolean matches(@NonNull PluginService<?> pluginService) {
        final var version = pluginService.getVersion().orElse(null);
        if (version == null) {
            return false;
        }
        return pluginService.getServiceAs(serviceType).isPresent() && version.equals(this.version);
    }
}
