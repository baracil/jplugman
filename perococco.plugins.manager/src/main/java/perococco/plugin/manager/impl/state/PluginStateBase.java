package Bastien Aracil.plugin.manager.impl.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugin.api.VersionedServiceClass;

import java.nio.file.Path;

@RequiredArgsConstructor
public abstract class PluginStateBase implements PluginState {

    @Getter
    protected final @NonNull PluginContext pluginContext;

    @Override
    public boolean isServiceAvailable(@NonNull VersionedServiceClass<?> requirement) {
        return pluginContext.isServiceAvailable(requirement);
    }

    @Override
    public boolean isFromBundle(@NonNull Path pluginBundleLocation) {
        return pluginContext.getPluginLocation().equals(pluginBundleLocation);
    }
}
