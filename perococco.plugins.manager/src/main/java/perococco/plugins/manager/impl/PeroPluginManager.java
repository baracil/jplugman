package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.PluginManager;

import java.nio.file.Path;

@RequiredArgsConstructor
public class PeroPluginManager implements PluginManager {

    private final @NonNull Application application;

    private final PluginRegistry registry = new PluginRegistry();

    @Override
    @Synchronized
    public void addPlugin(@NonNull Path pluginLocation) {
        PluginAdder.addPlugin(registry, pluginLocation, application.getVersion());
        PluginLinker.linkPlugins(registry, application);
    }

    private void linkAcceptedPlugins() {
        //TODO
    }

    @Override
    @Synchronized
    public void removePlugin(@NonNull Path pluginLocation) {
        //TODO
    }



}
