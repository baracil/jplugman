package jplugman.manager.impl.state;

import lombok.NonNull;

public class FailedState implements PluginState {

    @Override
    public @NonNull PluginState unInstall(@NonNull PluginContext pluginContext) {
        return new UnInstalledState();
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
        return true;
    }
}
