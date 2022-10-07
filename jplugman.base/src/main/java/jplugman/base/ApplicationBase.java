package jplugman.base;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Application;
import jplugman.api.PluginService;
import jplugman.api.VersionedService;
import jplugman.tools.Subscription;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public abstract class ApplicationBase<T> implements Application {


    private final @NonNull ImmutableSet<VersionedService> versionedServices;
    private final Map<PluginService, Subscription> subscriptions = new HashMap<>();
    private final Class<T> serviceType;

    @Override
    public @NonNull ImmutableSet<VersionedService> getApplicationServices(@NonNull Class<?> pluginExtensionType) {
        return versionedServices;
    }

    @Override
    public void plugService(@NonNull PluginService pluginService) {
        LOG.info("Plug service   : {}", pluginService);
        pluginService.getServiceAs(serviceType)
                     .flatMap(this::handleService)
                     .ifPresent(s -> subscriptions.put(pluginService, s));
    }

    @Override
    public void unplugService(@NonNull PluginService pluginService) {
        LOG.info("Unplug service : {}", pluginService);
        final var subscription = subscriptions.remove(pluginService);
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    protected abstract Optional<Subscription> handleService(@NonNull T service);
}
