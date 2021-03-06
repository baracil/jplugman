package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;

public class FailedState extends PluginStateBase {

    public FailedState(@NonNull PluginContext context) {
        super(context);
    }

    @Override
    public @NonNull PluginState unInstall() {
        return new UnInstalledState(this.getPluginContext());
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
