package Bastien Aracil.plugins.manager.impl.operators;

import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.Operator;
import Bastien Aracil.plugins.manager.impl.PluginData;
import Bastien Aracil.plugins.manager.impl.PluginState;
import Bastien Aracil.plugins.manager.impl.state.InstalledState;
import Bastien Aracil.plugins.manager.impl.state.PluggedState;
import Bastien Aracil.plugins.manager.impl.state.ResolvedState;

/**
 * Change {@link ResolvedState} and {@link PluggedState} to {@link InstalledState}
 */
public class ChangeToInstalled implements Operator {

    public static @NonNull ChangeToInstalled create() {
        return new ChangeToInstalled();
    }

    @Override
    public @NonNull PluginState operate(@NonNull PluginData pluginData) {
        if (pluginData.isResolved() || pluginData.isPlugged()) {
            return new InstalledState();
        }
        return pluginData.getState();
    }
}
