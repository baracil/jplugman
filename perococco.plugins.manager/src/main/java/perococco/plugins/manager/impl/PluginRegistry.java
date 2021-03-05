package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface PluginRegistry {

    /**
     * @param pluginData the data about the plugin (id, module layer, location, state)
     */
    void addPlugin(@NonNull PluginData pluginData);

    @NonNull Optional<PluginData> removePluginData(long pluginId);


    @NonNull PluginData getPluginData(long id);

    /**
     * @param filter the filter to apply to the stream of plugin data
     * @return a stream of all the filtered plugin data
     */
    @NonNull Stream<PluginData> streamPluginData(@NonNull Predicate<? super PluginData> filter);

    @NonNull ImmutableList<PluginData> getPluginData(@NonNull Predicate<? super PluginData> filter);

    default @NonNull Stream<PluginData> streamPluginData() {
        return streamPluginData(p -> true);
    }

}
