package Bastien Aracil.jplugman.manager.action;

import com.google.common.collect.ImmutableList;
import jplugman.api.Application;
import jplugman.api.Plugin;
import jplugman.loader.PluginLoader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import Bastien Aracil.jplugman.manager.EnrichedPlugin;
import Bastien Aracil.jplugman.manager.MutableVersionedServiceProvider;
import Bastien Aracil.jplugman.manager.state.PluginContext;

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

    private jplugman.loader.PluginLoader.Result loadingResult;
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
            loadingResult = PluginLoader.loadBundle(pluginLocation);
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

        for (Plugin<?> plugin : this.loadingResult.getPlugins()) {
            final var ep = EnrichedPlugin.create(plugin).orElse(null);

            if (ep != null) {
                LOG.debug("Add plugin {}", plugin.getServiceClass());
                pluginContexts.add(PluginContext.create(application, pluginServiceProvider, pluginLocation,
                                                        loadingResult.getPluginLayer(), ep));
            } else {
                LOG.debug("Skip plugin {}", plugin.getServiceClass());
            }
        }

        this.pluginContexts = pluginContexts;
    }

    private void createEmptyPluginContexts() {
        this.pluginContexts = ImmutableList.of();
    }


}
