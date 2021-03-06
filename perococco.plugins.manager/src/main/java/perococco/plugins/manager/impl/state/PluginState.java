package Bastien Aracil.plugins.manager.impl.state;

import lombok.NonNull;
import Bastien Aracil.plugins.api.VersionedServiceType;

public interface PluginState extends StateOperation<PluginState> {

    @Override
    @NonNull
    default PluginState unload() {
        return this;
    }

    @Override
    @NonNull
    default PluginState load() {
        return this;
    }

    @Override
    @NonNull
    default PluginState markResolved() {
        return this;
    }

    @Override
    @NonNull
    default PluginState setToInstalledState() {
        return this;
    }

    @Override
    @NonNull
    default PluginState unInstall() {
        return this;
    }

}
