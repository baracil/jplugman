package Bastien Aracil.plugins.manager.impl.operators;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.manager.impl.Operator;
import Bastien Aracil.plugins.manager.impl.PluginData;
import Bastien Aracil.plugins.manager.impl.PluginState;

/**
 * An operator that is applied only on a specific state
 * @param <S> the type for the state
 */
@RequiredArgsConstructor
public abstract class StateOperator<S extends PluginState> implements Operator {

    private final @NonNull Class<S> stateType;

    protected abstract @NonNull PluginState operator(@NonNull S state, @NonNull PluginData pluginData);

    @Override
    public @NonNull PluginState operate(@NonNull PluginData pluginData) {
        final var state = pluginData.getState();
        if (stateType.isInstance(state))  {
            return this.operator(stateType.cast(state), pluginData);
        }
        return state;
    }

}
