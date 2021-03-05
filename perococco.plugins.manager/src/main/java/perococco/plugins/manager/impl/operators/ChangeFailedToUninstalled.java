package Bastien Aracil.plugins.manager.impl.operators;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.Operator;
import Bastien Aracil.plugins.manager.impl.PluginData;
import Bastien Aracil.plugins.manager.impl.PluginState;
import Bastien Aracil.plugins.manager.impl.state.FailedState;
import Bastien Aracil.plugins.manager.impl.state.UnInstalledState;

/**
 * An operator that change {@link FailedState} to {@link UnInstalledState}
 */
public class ChangeFailedToUninstalled extends StateOperator<FailedState> {

    public static @NonNull Operator create() {
        return new ChangeFailedToUninstalled();
    }

    public ChangeFailedToUninstalled() {
        super(FailedState.class);
    }

    @Override
    public @NonNull PluginState operator(@NonNull FailedState state, @NonNull PluginData pluginData) {
        return new UnInstalledState();
    }

}
