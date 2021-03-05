package Bastien Aracil.plugins.manager.impl.operators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.impl.Operator;
import Bastien Aracil.plugins.manager.impl.PluginData;
import Bastien Aracil.plugins.manager.impl.PluginSpecificServiceProvider;
import Bastien Aracil.plugins.manager.impl.PluginState;
import Bastien Aracil.plugins.manager.impl.state.FailedState;
import Bastien Aracil.plugins.manager.impl.state.PluggedState;
import Bastien Aracil.plugins.manager.impl.state.ResolvedState;

public class Loader extends StateOperator<ResolvedState> {

    private final Application application;

    public Loader(Application application) {
        super(ResolvedState.class);
        this.application = application;
    }

    @Override
    public @NonNull PluginState operator(@NonNull ResolvedState state, @NonNull PluginData pluginData) {
        try {
            final var serviceProvider = PluginSpecificServiceProvider.create(pluginData.getPluginRequirements(), application.getServiceProvider());
            final var versionService = pluginData.loadService(serviceProvider);
            application.attachService(versionService);
            return new PluggedState(versionService);
        } catch (Throwable e) {
            return new FailedState();
        }
    }

}
