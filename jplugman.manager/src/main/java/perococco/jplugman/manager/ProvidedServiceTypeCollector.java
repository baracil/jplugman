package Bastien Aracil.jplugman.manager;

import lombok.NonNull;
import Bastien Aracil.jplugman.manager.state.PluginContext;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ProvidedServiceTypeCollector implements Collector<PluginContext, Map<Class<?>, PluginIdAndExtensionData>, Map<Class<?>, PluginIdAndExtensionData>> {

    @Override
    public Supplier<Map<Class<?>, PluginIdAndExtensionData>> supplier() {
        return HashMap::new;
    }

    @Override
    public BiConsumer<Map<Class<?>, PluginIdAndExtensionData>, PluginContext> accumulator() {
        return (t, p) -> PluginIdAndExtensionData.createWith(p).ifPresent(v -> accumulate(t, v));
    }

    @Override
    public BinaryOperator<Map<Class<?>, PluginIdAndExtensionData>> combiner() {
        return (t1, t2) -> {
            t2.values().forEach(v -> accumulate(t1, v));
            return t1;
        };
    }

    @Override
    public Function<Map<Class<?>, PluginIdAndExtensionData>, Map<Class<?>, PluginIdAndExtensionData>> finisher() {
        return t -> t;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

    private void accumulate(@NonNull Map<Class<?>, PluginIdAndExtensionData> map,
                            @NonNull PluginIdAndExtensionData toAdd) {
        final var serviceType = toAdd.getServiceType();
        final var newValue = Optional.ofNullable(map.get(serviceType))
                                     .map(existing -> max(existing, toAdd))
                                     .orElse(toAdd);
        map.put(serviceType, newValue);
    }

    public @NonNull PluginIdAndExtensionData max(@NonNull PluginIdAndExtensionData pst1, @NonNull PluginIdAndExtensionData pst2) {
        return pst1.getVersion().compareTo(pst2.getVersion()) >= 0 ? pst1 : pst2;
    }


}
