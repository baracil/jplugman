package jplugman.manager.impl.state;

import com.google.common.collect.ImmutableSet;
import jplugman.api.*;
import jplugman.manager.MutableServiceProvider;
import jplugman.manager.impl.EnrichedPlugin;
import jplugman.manager.impl.PeroExtension;
import jplugman.manager.impl.PluginSpecificServiceRegistry;
import jplugman.manager.impl.VersionedServiceClass;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@RequiredArgsConstructor
@Log4j2
public class PluginContext {

    private final @NonNull Application application;
    private final @NonNull MutableServiceProvider pluginServiceProvider;
    private final @NonNull Path pluginLocation;
    private final @NonNull ModuleLayer moduleLayer;
    private final @NonNull EnrichedPlugin<?> plugin;
    private final long id;


    private final static AtomicLong ID_GENERATOR = new AtomicLong(0);

    public static PluginContext create(@NonNull Application application,
                                       @NonNull MutableServiceProvider pluginServiceProvider,
                                       @NonNull Path pluginLocation,
                                       @NonNull ModuleLayer pluginLayer,
                                       @NonNull EnrichedPlugin<?> plugin) {
        return new PluginContext(
                application,
                pluginServiceProvider,
                pluginLocation,
                pluginLayer,
                plugin,
                ID_GENERATOR.incrementAndGet()
        );
    }

    public @NonNull Optional<VersionedServiceClass> getProvidedService() {
        return plugin.getProvidedService();
    }

    public @NonNull ImmutableSet<Requirement<?>> getPluginRequirements() {
        return plugin.getRequirements();
    }

    public @NonNull ServiceProvider getApplicationServiceProvider() {
        return application.getServiceProvider(plugin.getExtensionClass());
    }

    public @NonNull Extension loadService(ServiceRegistry serviceRegistry) {
        final var extension = plugin.loadExtension(moduleLayer, serviceRegistry);
        return plugin.getProvidedService()
                     .map(p -> PeroExtension.withProvidedService(extension, p))
                     .orElseGet(() -> PeroExtension.withoutProvidedService(extension));
    }

    public void plugExtension(@NonNull Extension extension) {
        LOG.debug("Plug   service : {}", extension.getType());
        this.application.plugExtension(extension);
        this.pluginServiceProvider.addExtension(extension);
    }

    public void unplugService(@NonNull Extension extension) {
        LOG.debug("Unplug service : {}", extension);
        this.pluginServiceProvider.removeExtension(extension);
        this.application.unplugExtension(extension);
    }

    public boolean isServiceAvailable(@NonNull Requirement<?> requirement) {
        final var serviceProvider = pluginServiceProvider.thenSearch(getApplicationServiceProvider());
        return serviceProvider.hasService(requirement);
    }

    @Override
    public String toString() {
        return "PluginContext{" + id + ", " + pluginLocation.getFileName() +
                ", service=" + plugin.getExtensionClass() +
                '}';
    }

    public @NonNull PluggedState load() {
        final var serviceProvider = PluginSpecificServiceRegistry.create(
                plugin.getRequirements(),
                this.getApplicationServiceProvider().thenSearch(this.pluginServiceProvider));
        final var extension = this.loadService(serviceProvider);
        this.plugExtension(extension);
        return new PluggedState(extension);

    }

    public boolean pluginProvides(@NonNull Requirement<?> requirement) {
        return plugin.getProvidedService().filter(v -> v.provides(requirement)).isPresent();
    }
}


