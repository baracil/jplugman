package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.manager.impl.state.AcceptedPlugin;
import Bastien Aracil.plugins.manager.impl.state.LoadedPlugin;
import Bastien Aracil.plugins.manager.impl.state.RejectedPlugin;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PluginAdder {

    public static void addPlugin(@NonNull PluginRegistry registry, @NonNull Path pluginLocation, int applicationVersion) {
        new PluginAdder(pluginLocation, applicationVersion, registry).addPlugin();
    }

    private final @NonNull Path pluginLocation;
    private final int applicationVersion;
    private final @NonNull PluginRegistry registry;


    private void addPlugin() {
        if (isPluginAlreadyLoaded()) {
            return;
        }
        this.loadAndInitializePluginInfo();
        this.checkPluginsCompatibility();
        this.checkPluginsConflict();
    }

    private boolean isPluginAlreadyLoaded() {
        return registry.containsPlugin(pluginLocation);
    }

    private void loadAndInitializePluginInfo() {
        final var result = Plugin.load(pluginLocation);
        final Set<PluginInfo> initialPluginInfo = result.getPlugins()
                                                        .stream()
                                                        .map(p -> new LoadedPlugin(result.getPluginLayer(), p))
                                                        .collect(Collectors.toSet());
        registry.register(pluginLocation,initialPluginInfo);
    }

    private void checkPluginsCompatibility() {
        registry.updatePluginInfo(pluginLocation, LoadedPlugin.class, p -> p.checkCompatibility(applicationVersion));
    }

    private void checkPluginsConflict() {
        registry.updatePluginInfo(pluginLocation, AcceptedPlugin.class, this::checkForConflict);
    }

    private @NonNull PluginInfo checkForConflict(@NonNull AcceptedPlugin pluginInfo) {
        final var plugins = registry.findNotRejectedPluginInfoProviding(pluginInfo.getProvidedServiceType());
        plugins.remove(pluginInfo);
        if (plugins.isEmpty()) {
            return pluginInfo;
        }
        return RejectedPlugin.from(pluginInfo);
    }

}
