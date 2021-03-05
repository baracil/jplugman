package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.PluginState;

public class InstalledState implements PluginState {

    @Override
    public boolean isInstalled() {
        return true;
    }

    public @NonNull ResolvedState toResolved() {
        return new ResolvedState();
    }
}
