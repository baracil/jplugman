package Bastien Aracil.plugins.manager.impl.state;

import Bastien Aracil.plugins.manager.impl.PluginState;

public class FailedState implements PluginState {

    @Override
    public boolean isFailed() {
        return true;
    }
}
