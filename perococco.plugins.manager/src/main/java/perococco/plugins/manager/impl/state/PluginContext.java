package Bastien Aracil.plugins.manager.impl.state;

import com.google.common.collect.ImmutableSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import Bastien Aracil.plugins.api.*;
import Bastien Aracil.plugins.manager.Application;

import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@RequiredArgsConstructor
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

    public @NonNull VersionedServiceType<?> getProvidedService() {
        return plugin.getProvidedService();
    }

    public @NonNull ImmutableSet<VersionedServiceType<?>> getPluginRequirements() {
        return plugin.getRequirements();
    }

    public @NonNull VersionedServiceProvider getApplicationServiceProvider() {
        return application.getServiceProvider(plugin.getProvidedService());
    }

    public @NonNull VersionedService loadService(ServiceProvider serviceProvider) {
        return plugin.loadService(moduleLayer,serviceProvider);
    }

    public void attachService(VersionedService versionService) {
        this.application.plugService(versionService);
        this.pluginServiceProvider.addVersionedService(versionService);
    }

    public void detachService(VersionedService versionedService) {
        this.pluginServiceProvider.removeVersionedService(versionedService);
        this.application.unplugService(versionedService);
    }

    public boolean isServiceAvailable(@NonNull VersionedServiceType<?> requirement) {
        final var serviceProvider = pluginServiceProvider.thenSearch(getApplicationServiceProvider());
        return serviceProvider.hasService(requirement);
    }
}


