package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import Bastien Aracil.plugins.api.Plugin;

public interface PluginInfo {

    long getId();
    @NonNull ModuleLayer getModuleLayer();
    @NonNull Plugin getPlugin();
    @NonNull PluginState getState();


    default boolean isRejected() {
        return getState() == PluginState.REJECTED;
    }

    default @NonNull Class<?> getProvidedServiceType() {
        return getPlugin().getProvidedServiceType().getServiceType();
    }

    default boolean isWaitingForLinking() {
        return getState() == PluginState.ACCEPTED;
    }



    interface Factory {
        @NonNull PluginInfo create(long id, @NonNull ModuleLayer moduleLayer, @NonNull Plugin plugin);
    }

}
