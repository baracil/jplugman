package Bastien Aracil.plugins.manager.impl.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.VersionedServiceType;

@RequiredArgsConstructor
public abstract class PluginStateBase implements PluginState {

    @Getter
    protected final @NonNull PluginContext pluginContext;

    @Override
    public boolean isServiceAvailable(@NonNull VersionedServiceType<?> requirement) {
        return pluginContext.isServiceAvailable(requirement);
    }

}
