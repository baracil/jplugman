package Bastien Aracil.plugins.manager.impl;

import lombok.NonNull;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

public class PluginRegistry {

    private final @NonNull Map<Path, Set<PluginInfo>> plugins = new HashMap<>();

    /**
     * @param pluginLocation the path to the plugin file
     * @param updater        the updater to apply to all {@link PluginInfo} associated with the plugin
     * @return true if at least one plugin info state changed
     */
    public <P extends PluginInfo> boolean updatePluginInfo(@NonNull Path pluginLocation, @NonNull Class<P> pluginInfoType, @NonNull Function<? super P, ? extends PluginInfo> updater) {
        final var value = plugins.get(pluginLocation);

        final var evolutions = value.stream()
                                    .map(p -> {
                                        if (pluginInfoType.isInstance(p)) {
                                            return Evolution.withUpdater(pluginInfoType.cast(p), updater);
                                        } else {
                                            return Evolution.withoutChange(p);
                                        }

                                    })
                                    .collect(Collectors.toList());

        {
            final var newValues = evolutions.stream()
                                            .map(Evolution::getNewValue)
                                            .collect(Collectors.toSet());
            plugins.put(pluginLocation, newValues);
        }

        return evolutions.stream().anyMatch(Evolution::isDifferenteState);
    }
    public boolean updatePluginInfo(@NonNull Path pluginLocation, @NonNull UnaryOperator<PluginInfo> updater) {
        return this.updatePluginInfo(pluginLocation,PluginInfo.class,updater);
    }

    public boolean containsPlugin(Path pluginLocation) {
        return plugins.containsKey(pluginLocation);
    }

    public void register(@NonNull Path pluginLocation, @NonNull Set<PluginInfo> initialPluginInfo) {
        plugins.put(pluginLocation, initialPluginInfo);
    }

    public @NonNull Set<Path> pluginPaths() {
        return Collections.unmodifiableSet(plugins.keySet());
    }

    public @NonNull Set<PluginInfo> findNotRejectedPluginInfoProviding(@NonNull Class<?> providedServiceType) {
        return plugins.values()
                      .stream()
                      .flatMap(Collection::stream)
                      .filter(p -> !p.isRejected())
                      .filter(p -> p.getProvidedServiceType().equals(providedServiceType))
                      .collect(Collectors.toSet());

    }
}
