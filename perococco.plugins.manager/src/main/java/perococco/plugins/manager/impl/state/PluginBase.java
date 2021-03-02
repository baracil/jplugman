package Bastien Aracil.plugins.manager.impl.state;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.manager.impl.PluginInfo;

@RequiredArgsConstructor
@Getter
public abstract class PluginBase implements PluginInfo {

    private final long id;
    private final @NonNull ModuleLayer moduleLayer;
    private final @NonNull Plugin plugin;


    public @NonNull PluginInfo createPluginInfo(@NonNull Factory constructor) {
        return constructor.create(id,moduleLayer,plugin);
    }
}
