package Bastien Aracil.jplugman.manager.state;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Application;
import jplugman.api.ExtensionData;
import jplugman.api.PluginService;
import jplugman.api.Requirement;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import Bastien Aracil.jplugman.manager.*;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@RequiredArgsConstructor
@Log4j2
public class PluginContext implements PluginsStateAction {

    private final @NonNull Application application;
    private final @NonNull VersionedServiceProvider applicationServiceProvider;
    private final @NonNull MutableVersionedServiceProvider pluginServiceProvider;
    private final @NonNull Path pluginLocation;
    private final @NonNull ModuleLayer moduleLayer;
    private final @NonNull EnrichedPlugin<?> plugin;
    private final long id;


    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    public static PluginContext create(@NonNull Application application,
                                       @NonNull MutableVersionedServiceProvider pluginServiceProvider,
                                       @NonNull Path pluginLocation,
                                       @NonNull ModuleLayer pluginLayer,
                                       @NonNull EnrichedPlugin<?> plugin) {
        return new PluginContext(
                application,
                ImmutableVersionedServiceProvider.of(application.getApplicationServices(plugin.getServiceClass())),
                pluginServiceProvider,
                pluginLocation,
                pluginLayer,
                plugin,
                ID_GENERATOR.incrementAndGet()
        );
    }

    @Override
    public @NonNull String getPluginInfo() {
        return plugin+ " '" + pluginLocation.getFileName() + "'";
    }

    public @NonNull Optional<? extends ExtensionData<?>> getExtensionData() {
        return plugin.getExtensionData();
    }

    public @NonNull ImmutableSet<Requirement<?>> getPluginRequirements() {
        return plugin.getRequirements();
    }

    public void plugService(@NonNull PluginService<?> pluginService) {
        LOG.debug("Plug   service : {}", pluginService);
        this.application.plugService(pluginService);
        this.pluginServiceProvider.addPluginService(pluginService);
    }

    public void unplugService(@NonNull PluginService<?> pluginService) {
        LOG.debug("Unplug service : {}", pluginService);
        this.pluginServiceProvider.removePluginService(pluginService);
        this.application.unplugService(pluginService);
    }

    @Override
    public String toString() {
        return "PluginContext{" + id + ", " + pluginLocation.getFileName() +
                ", service=" + plugin.getServiceClass() +
                '}';
    }

    public @NonNull PluginService<?> load() {
        final var serviceProvider = PluginSpecificServiceProvider.create(
                plugin.getRequirements(),
                applicationServiceProvider.thenSearch(this.pluginServiceProvider)
        );

        return this.plugin.load(moduleLayer, serviceProvider);
    }

    public boolean pluginProvides(@NonNull Requirement<?> requirement) {
        return plugin.getExtensionData().filter(d -> d.provides(requirement)).isPresent();
    }
}


