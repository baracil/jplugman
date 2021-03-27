package jplugman.manager.impl.state;

import lombok.NonNull;

public interface PluginState extends StateOperation<PluginState> {

    @Override
    @NonNull
    default PluginState unload(@NonNull PluginContext context) {
        return this;
    }

    @Override
    @NonNull
    default PluginState load(@NonNull PluginContext context) {
        return this;
    }

    @Override
    @NonNull
    default PluginState markResolved(@NonNull PluginContext context) {
        return this;
    }

    @Override
    @NonNull
    default PluginState setToInstalledState(@NonNull PluginContext context) {
        return this;
    }

    @Override
    @NonNull
    default PluginState unInstall(@NonNull PluginContext context) {
        return this;
    }

}
