package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.VersionedServiceProvider;
import Bastien Aracil.plugins.manager.impl.PluginInfo;
import Bastien Aracil.plugins.manager.impl.PluginState;

public class AcceptedPlugin extends PluginBase {

    public AcceptedPlugin(long id, @NonNull ModuleLayer moduleLayer, @NonNull Plugin plugin) {
        super(id, moduleLayer, plugin);
    }

    @Override
    public @NonNull PluginState getState() {
        return PluginState.LOADED;
    }


    public boolean areRequirementsFulFilled(@NonNull VersionedServiceProvider serviceProvider) {
        final var requirements = getPlugin().getRequirements();
        return requirements.stream()
                           .allMatch(serviceProvider::hasService);
    }

    public @NonNull PluginInfo resolved() {
        return createPluginInfo(ResolvedPlugin::new);
    }
}
