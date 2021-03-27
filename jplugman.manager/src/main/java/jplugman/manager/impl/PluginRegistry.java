package jplugman.manager.impl;

import com.google.common.collect.ImmutableList;
import jplugman.manager.MutableServiceProvider;
import jplugman.manager.impl.state.PluginData;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface PluginRegistry extends MutableServiceProvider {

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
     * @param pluginId the id of the searched plugin data
     * @return the plugin data with the provided id.
     * @throws IllegalArgumentException if no plugin data exists with the provided id
     */
    @NonNull PluginData getPluginData(long pluginId);

    /**
     * @param filter the filter to apply to the stream of plugin data
     * @return a stream of all the filtered plugin data
     */
    @NonNull Stream<PluginData> streamPluginData(@NonNull Predicate<? super PluginData> filter);

    @NonNull ImmutableList<PluginData> getPluginData(@NonNull Predicate<? super PluginData> filter);

}
