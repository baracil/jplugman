package Bastien Aracil.jplugman.manager.state;

import jplugman.api.PluginService;
import lombok.NonNull;

public interface PluginsStateAction {

    @NonNull String getPluginInfo();

    @NonNull PluginService<?> load();

    void unplugService(@NonNull PluginService<?> pluginService);

    void plugService(PluginService<?> pluginService);

}
