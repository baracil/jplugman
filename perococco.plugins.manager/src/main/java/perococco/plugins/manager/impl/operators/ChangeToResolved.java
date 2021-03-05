package Bastien Aracil.plugins.manager.impl.operators;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.Operator;
import Bastien Aracil.plugins.manager.impl.PluginData;
import Bastien Aracil.plugins.manager.impl.PluginState;
import Bastien Aracil.plugins.manager.impl.state.InstalledState;
import Bastien Aracil.plugins.manager.impl.state.ResolvedState;

public class ChangeToResolved extends StateOperator<InstalledState> {

    public static Operator create() {
        return new ChangeToResolved();
    }

    public ChangeToResolved() {
        super(InstalledState.class);
    }

    @Override
    public @NonNull PluginState operator(@NonNull InstalledState state, @NonNull PluginData pluginData) {
        return new ResolvedState();
    }

}
