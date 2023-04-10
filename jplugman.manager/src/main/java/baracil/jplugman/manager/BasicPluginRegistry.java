package baracil.jplugman.manager;

import baracil.jplugman.manager.state.PluginData;
import lombok.NonNull;
import lombok.experimental.Delegate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BasicPluginRegistry implements PluginRegistry {

    @Delegate
    private final MutableVersionedServiceProvider serviceProvider = new PluginServiceRegistry();

    private final Map<Long, PluginData> pluginInfoById = new HashMap<>();

    @Override
    public @NonNull Optional<PluginData> removePluginData(long pluginId) {
        return Optional.ofNullable(this.pluginInfoById.remove(pluginId));
    }

    @Override
    public @NonNull List<PluginData> getPluginData(@NonNull Predicate<? super PluginData> filter) {
        return streamPluginData(filter).collect(Collectors.toList());
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
