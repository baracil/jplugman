package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.PluginSpecificServiceProvider;

public class ResolvedState extends PluginStateBase {

    public ResolvedState(@NonNull PluginContext context) {
        super(context);
    }

    @Override
    public @NonNull PluginState load() {
        try {
            final var serviceProvider = PluginSpecificServiceProvider.create(pluginContext.getPluginRequirements(), pluginContext.getApplicationServiceProvider());
            final var versionService = pluginContext.loadService(serviceProvider);
            pluginContext.attachService(versionService);
            return new PluggedState(getPluginContext(), versionService);
        } catch (Throwable e) {
            return new FailedState(getPluginContext());
        }

    }

    @Override
    public @NonNull PluginState unload() {
        return new InstalledState(getPluginContext());
    }

    @Override
    public boolean isInInstalledState() {
        return false;
    }

    @Override
    public boolean isInPluggedState() {
        return false;
    }

    @Override
    public boolean isInFailedState() {
        return false;
    }
}
