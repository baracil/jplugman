package Bastien Aracil.jplugman.manager.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class InstalledState implements PluginState {

    private final @NonNull PluginsStateAction pluginsStateAction;

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
    public @NonNull PluginState unInstall() {
        return new UnInstalledState();
    }

    @Override
    public @NonNull PluginState markResolved() {
        return new ResolvedState(pluginsStateAction);
    }
}
