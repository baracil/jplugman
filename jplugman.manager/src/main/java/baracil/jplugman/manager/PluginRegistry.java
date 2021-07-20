package baracil.jplugman.manager;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import baracil.jplugman.manager.state.PluginData;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface PluginRegistry extends MutableVersionedServiceProvider {

    /**
     * @param pluginData the data about the plugin (id, module layer, location, state)
     */
    void addPluginData(@NonNull PluginData pluginData);

    /**
     * @param pluginId the id of the plugin to remove
     * @return an optional containing the removed plugin data, an empty optional if no plugin data
     * exists with the provided id
     */
    @NonNull Optional<PluginData> removePluginData(long pluginId);

    /**
     * @param filter the filter to apply to the stream of plugin data
     * @return a stream of all the filtered plugin data
     */
    @NonNull Stream<PluginData> streamPluginData(@NonNull Predicate<? super PluginData> filter);

    @NonNull ImmutableList<PluginData> getPluginData(@NonNull Predicate<? super PluginData> filter);

}
