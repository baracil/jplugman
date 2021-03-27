package Bastien Aracil.jplugman.manager.state;

import lombok.NonNull;

public interface PluginState {

    boolean isInInstalledState();

    boolean isInPluggedState();

    boolean isInFailedState();

    @NonNull
    default PluginState unload(@NonNull PluginContext context) {
        return this;
    }

    @NonNull
    default PluginState load(@NonNull PluginContext context) {
        return this;
    }

    @NonNull
    default PluginState markResolved(@NonNull PluginContext context) {
        return this;
    }

    @NonNull
    default PluginState setToInstalledState(@NonNull PluginContext context) {
        return this;
    }

    @NonNull
    default PluginState unInstall(@NonNull PluginContext context) {
        return this;
    }

}
