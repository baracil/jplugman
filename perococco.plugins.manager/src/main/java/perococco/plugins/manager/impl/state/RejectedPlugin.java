package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.manager.impl.PluginInfo;
import Bastien Aracil.plugins.manager.impl.PluginState;

public class RejectedPlugin extends PluginBase {

    public static @NonNull PluginInfo from(PluginInfo pluginInfo) {
        return new RejectedPlugin(pluginInfo.getId(), pluginInfo.getModuleLayer(),pluginInfo.getPlugin());
    }

    public RejectedPlugin(long id, @NonNull ModuleLayer moduleLayer, @NonNull Plugin plugin) {
        super(id, moduleLayer, plugin);
    }

    @Override
    public @NonNull PluginState getState() {
        return PluginState.LOADED;
    }


}
