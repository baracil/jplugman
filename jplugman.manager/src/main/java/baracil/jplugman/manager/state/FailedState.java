package baracil.jplugman.manager.state;

import lombok.NonNull;

public class FailedState implements PluginState {


    @Override
    public @NonNull PluginState unInstall() {
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
