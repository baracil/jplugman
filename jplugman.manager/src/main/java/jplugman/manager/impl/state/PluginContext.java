package jplugman.manager.impl.state;

import com.google.common.collect.ImmutableSet;
import jplugman.api.*;
import jplugman.manager.Application;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import jplugman.api.*;
import jplugman.manager.impl.MutableVersionedServiceProvider;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@RequiredArgsConstructor
@Log4j2
public class PluginContext {

    private final @NonNull Application application;
    private final @NonNull MutableVersionedServiceProvider pluginServiceProvider;
    private final @NonNull Path pluginLocation;
    private final long id;
    private final @NonNull ModuleLayer moduleLayer;
    private final @NonNull Plugin plugin;


    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    public static PluginContext create(@NonNull Application application,
                                       @NonNull MutableVersionedServiceProvider pluginServiceProvider,
                                       @NonNull Path pluginLocation,
                                       @NonNull ModuleLayer pluginLayer,
                                       @NonNull Plugin plugin) {
        return new PluginContext(
                application,
                pluginServiceProvider,
                pluginLocation,
                ID_GENERATOR.incrementAndGet(),
                pluginLayer,
                plugin
        );
    }

    public @NonNull VersionedServiceClass<?> getProvidedService() {
        return plugin.getProvidedService();
    }

    public @NonNull ImmutableSet<VersionedServiceClass<?>> getPluginRequirements() {
        return plugin.getRequirements();
    }

    public @NonNull VersionedServiceProvider getApplicationServiceProvider() {
        return application.getServiceProvider(plugin.getProvidedService());
    }

    public @NonNull VersionedService loadService(ServiceProvider serviceProvider) {
        return plugin.loadService(moduleLayer,serviceProvider);
    }

    public void plugService(@NonNull VersionedService versionedService) {
        LOG.debug("Plug   service : {}",versionedService);
        this.application.plugService(versionedService);
        this.pluginServiceProvider.addVersionedService(versionedService);
    }

    public void unplugService(@NonNull VersionedService versionedService) {
        LOG.debug("Unplug service : {}",versionedService);
        this.pluginServiceProvider.removeVersionedService(versionedService);
        this.application.unplugService(versionedService);
    }

    public boolean isServiceAvailable(@NonNull VersionedServiceClass<?> requirement) {
        final var serviceProvider = pluginServiceProvider.thenSearch(getApplicationServiceProvider());
        return serviceProvider.hasService(requirement);
    }

    @Override
    public String toString() {
        return "PluginContext{" + id+ ", " + pluginLocation.getFileName() +
                ", service=" + plugin.getProvidedService() +
                '}';
    }
}


