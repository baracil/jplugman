package Bastien Aracil.plugins.manager.impl;

public interface PluginState {

    default boolean isInstalled() { return false; }

    default boolean isFailed() {
        return false;
    }

    default boolean isResolved() {
        return false;
    }

    default boolean isPlugged() {
        return false;
    }
}
