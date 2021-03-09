package jplugman.manager.impl.state;

import lombok.NonNull;

public class InstalledState extends PluginStateBase {

    public InstalledState(@NonNull PluginContext context) {
        super(context);
    }

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
        return new UnInstalledState(getPluginContext());
    }

    @Override
    public @NonNull PluginState markResolved() {
        return new ResolvedState(getPluginContext());
    }
}
