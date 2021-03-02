package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.ServiceProvider;
import Bastien Aracil.plugins.api.VersionedService;
import Bastien Aracil.plugins.api.VersionedServiceProvider;
import Bastien Aracil.plugins.manager.impl.PluginInfo;
import Bastien Aracil.plugins.manager.impl.PluginSpecificServiceProvider;
import Bastien Aracil.plugins.manager.impl.PluginState;

public class ResolvedPlugin extends PluginBase {

    public ResolvedPlugin(long id, @NonNull ModuleLayer moduleLayer, @NonNull Plugin plugin) {
        super(id, moduleLayer, plugin);
    }

    @Override
    public @NonNull PluginState getState() {
        return PluginState.RESOLVED;
    }

    public @NonNull VersionedService loadService(@NonNull VersionedServiceProvider versionedServiceProvider) {
        final var plugin = getPlugin();
        final var moduleLayer = getModuleLayer();

        return plugin.loadService(moduleLayer, PluginSpecificServiceProvider.create(plugin.getRequirements(),versionedServiceProvider));
    }

    public @NonNull PluginInfo failed() {
        return createPluginInfo(FailedPlugin::new);
    }

    public @NonNull PluginInfo plugged() {
        return createPluginInfo(PluggedPlugin::new);
    }
}
