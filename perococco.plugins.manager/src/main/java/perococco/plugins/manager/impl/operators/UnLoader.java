package Bastien Aracil.plugins.manager.impl.operators;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.impl.Operator;
import Bastien Aracil.plugins.manager.impl.PluginData;
import Bastien Aracil.plugins.manager.impl.PluginState;
import Bastien Aracil.plugins.manager.impl.state.InstalledState;
import Bastien Aracil.plugins.manager.impl.state.PluggedState;

public class UnLoader extends StateOperator<PluggedState> {

    private final @NonNull Application application;

    public UnLoader(@NonNull Application application) {
        super(PluggedState.class);
        this.application = application;
    }

    @Override
    public @NonNull PluginState operator(@NonNull PluggedState state, @NonNull PluginData pluginData) {
        application.detachService(state.getVersionedService());
        return new InstalledState();
    }

}
