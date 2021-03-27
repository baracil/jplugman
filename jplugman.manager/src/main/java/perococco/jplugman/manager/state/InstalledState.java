package Bastien Aracil.jplugman.manager.state;

import lombok.NonNull;

public class InstalledState implements PluginState {

    @Override
    public boolean isInInstalledState() {
        return true;
    }

    @Override
    public boolean isInPluggedState() {
        return false;
    }

    @Override
    public boolean isInFailedState() {
        return false;
    }

    @Override
    public @NonNull PluginState unInstall(@NonNull PluginContext pluginContext) {
        return new UnInstalledState();
    }

    @Override
    public @NonNull PluginState markResolved(@NonNull PluginContext pluginContext) {
        return new ResolvedState();
    }
}
