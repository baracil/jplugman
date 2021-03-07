package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.experimental.Delegate;
import Bastien Aracil.plugins.manager.impl.state.PluginData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BasicPluginRegistry implements PluginRegistry {

    @Delegate
    private final MutableVersionedServiceProvider serviceProvider = new HashSetVersionedServiceProvider();

    private final Map<Long, PluginData> pluginInfoById = new HashMap<>();

    @Override
    public @NonNull PluginData getPluginData(long pluginId) {
        final var result = pluginInfoById.get(pluginId);
        if (result == null) {
            throw new IllegalArgumentException("No node has the id '"+ pluginId +"'");
        }
        return result;
    }

    @Override
    public @NonNull Optional<PluginData> removePluginData(long pluginId) {
        return Optional.ofNullable(this.pluginInfoById.remove(pluginId));
    }

    @Override
    public @NonNull ImmutableList<PluginData> getPluginData(@NonNull Predicate<? super PluginData> filter) {
        return streamPluginData(filter).collect(ImmutableList.toImmutableList());
    }

    @Override
    public void addPluginData(@NonNull PluginData pluginData) {
        this.pluginInfoById.put(pluginData.getId(), pluginData);
    }

    @Override
    public @NonNull Stream<PluginData> streamPluginData(@NonNull Predicate<? super PluginData> filter) {
        return pluginInfoById.values()
                             .stream()
                             .filter(filter);
    }

}
