package Bastien Aracil.jplugman.manager;

import jplugman.api.PluginService;
import lombok.NonNull;

public interface MutableVersionedServiceProvider extends VersionedServiceProvider {

    void addPluginService(@NonNull PluginService<?> pluginService);

    void removePluginService(@NonNull PluginService<?> pluginService);

}
