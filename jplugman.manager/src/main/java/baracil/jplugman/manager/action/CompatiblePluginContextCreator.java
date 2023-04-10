package baracil.jplugman.manager.action;

import baracil.jplugman.manager.EnrichedPlugin;
import baracil.jplugman.manager.MutableVersionedServiceProvider;
import baracil.jplugman.manager.state.PluginContext;
import jplugman.api.Application;
import jplugman.api.Plugin;
import jplugman.loader.PluginLoader;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

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

        for (Plugin plugin : this.loadingResult.getPlugins()) {
            final var ep = EnrichedPlugin.create(plugin);

            LOG.debug("Add plugin {}", plugin.getServiceClass());
            pluginContexts.add(PluginContext.create(application, pluginServiceProvider, pluginLocation,
                                                    loadingResult.getPluginLayer(), ep));
        }

        this.pluginContexts = pluginContexts;
    }

    private void createEmptyPluginContexts() {
        this.pluginContexts = List.of();
    }


}
