package baracil.jplugman.manager.state;

import jplugman.api.PluginService;
import lombok.NonNull;

public interface PluginsStateAction {

    @NonNull String getPluginInfo();

    void unplugService(@NonNull PluginService pluginService);

    @NonNull PluginService loadAndPlugService();

}
