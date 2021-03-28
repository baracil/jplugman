package Bastien Aracil.jplugman.manager.state;

import lombok.NonNull;

public interface PluginState {

    boolean isInInstalledState();

    boolean isInPluggedState();

    boolean isInFailedState();

    @NonNull
    default PluginState unload() {
        return this;
    }

    @NonNull
    default PluginState load() {
        return this;
    }

    @NonNull
    default PluginState markResolved() {
        return this;
    }

    @NonNull
    default PluginState setToInstalledState() {
        return this;
    }

    @NonNull
    default PluginState unInstall() {
        return this;
    }

}
