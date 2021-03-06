package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;

public class UnInstalledState extends PluginStateBase {

    public UnInstalledState(@NonNull PluginContext context) {
        super(context);
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
