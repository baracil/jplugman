package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.PluginLoader;
import Bastien Aracil.plugins.api.Version;

import java.nio.file.Path;
import java.util.function.Consumer;

@RequiredArgsConstructor
@Log4j2
public class PluginAdder {

    public static void addPlugin(@NonNull Consumer<? super PluginData> pluginInfoConsumer,
                                 @NonNull Path pluginLocation,
                                 @NonNull Version applicationVersion) {
        new PluginAdder(pluginLocation, applicationVersion, pluginInfoConsumer).addPlugin();
    }

    private final @NonNull Path pluginLocation;
    private final @NonNull Version applicationVersion;
    private final @NonNull Consumer<? super PluginData> pluginDataConsumer;

    private PluginLoader.Result loadingResult;


    private void addPlugin() {
        this.loadPlugin();
        if (isLoadingSuccessful()) {
            this.addCompatiblePluginToRegistry();
        }
    }

    private void loadPlugin() {
        try {
            loadingResult = Plugin.loadBundle(pluginLocation);
        } catch (Exception e) {
            LOG.warn("Fail to load plugin '{}' : {}", pluginLocation, e.getMessage());
            LOG.debug(e);
            loadingResult = null;
        }
    }

    private boolean isLoadingSuccessful() {
        return loadingResult != null;
    }

    private void addCompatiblePluginToRegistry() {
        for (Plugin plugin : this.loadingResult.getPlugins()) {
            if (plugin.getVersionCompatibility().isCompatible(applicationVersion)) {
                LOG.debug("Add plugin {}",plugin.getProvidedServiceType());
                pluginDataConsumer.accept(PluginData.installed(pluginLocation, loadingResult.getPluginLayer(), plugin));
            } else {
                LOG.warn("Incompatible plugin version for {}",plugin.getProvidedServiceType());
            }
        }
    }


}
