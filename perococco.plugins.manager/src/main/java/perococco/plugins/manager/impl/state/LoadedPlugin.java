package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.manager.impl.PluginInfo;
import Bastien Aracil.plugins.manager.impl.PluginState;

import java.util.concurrent.atomic.AtomicLong;

public class LoadedPlugin extends PluginBase {

    private final static AtomicLong ID = new AtomicLong(0);

    public LoadedPlugin(@NonNull ModuleLayer moduleLayer, @NonNull Plugin plugin) {
        super(ID.getAndIncrement(), moduleLayer, plugin);
    }

    @Override
    public @NonNull PluginState getState() {
        return PluginState.LOADED;
    }

    public @NonNull PluginInfo checkCompatibility(int applicationCompatibility) {
        final boolean isCompatible = getPlugin().getVersionCompatibility() == applicationCompatibility;
        return createPluginInfo(isCompatible?AcceptedPlugin::new:RejectedPlugin::new);
    }


}
