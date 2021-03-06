package Bastien Aracil.plugins.manager.impl.action;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import Bastien Aracil.plugins.api.MutableVersionedServiceProvider;
import Bastien Aracil.plugins.api.Plugin;
import Bastien Aracil.plugins.api.PluginLoader;
import Bastien Aracil.plugins.manager.Application;
import Bastien Aracil.plugins.manager.impl.state.PluginContext;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class CompatiblePluginContextCreator {

    public static @NonNull List<PluginContext> create(
            @NonNull Application application,
            @NonNull MutableVersionedServiceProvider pluginServiceProvider,
            @NonNull Path bundleLocation
    ) {
        return new CompatiblePluginContextCreator(application, pluginServiceProvider, bundleLocation).create();
    }

    private final @NonNull Application application;
    private final @NonNull MutableVersionedServiceProvider pluginServiceProvider;
    private final @NonNull Path pluginLocation;

    private PluginLoader.Result loadingResult;
    private List<PluginContext> pluginContexts;

    private @NonNull List<PluginContext> create() {
        this.loadPlugin();
        if (isLoadingSuccessful()) {
            this.createPluginContexts();
        } else {
            this.createEmptyPluginContexts();
        }

        return this.pluginContexts;
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

    private void createPluginContexts() {
        final List<PluginContext> pluginContexts = new ArrayList<>(loadingResult.getPlugins().size());

        final var applicationVersion = application.getVersion();

        for (Plugin plugin : this.loadingResult.getPlugins()) {
            if (plugin.getApplicationVersion().isCompatible(applicationVersion)) {
                LOG.debug("Add plugin {}", plugin.getProvidedService());
                pluginContexts.add(PluginContext.create(application, pluginServiceProvider, pluginLocation, loadingResult.getPluginLayer(), plugin));
            } else {
                LOG.warn("Incompatible plugin version for {}", plugin.getProvidedService());
            }
        }

        this.pluginContexts = pluginContexts;
    }

    private void createEmptyPluginContexts() {
        this.pluginContexts = ImmutableList.of();
    }


}
