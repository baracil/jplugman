package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.manager.impl.PluginState;

public class FailedPlugin extends PluginBase {

    public FailedPlugin(long id, @NonNull ModuleLayer moduleLayer, @NonNull Plugin plugin) {
        super(id, moduleLayer, plugin);
    }

    @Override
    public @NonNull PluginState getState() {
        return PluginState.FAILED;
    }
}
