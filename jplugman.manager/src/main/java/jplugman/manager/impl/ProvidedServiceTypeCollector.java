package jplugman.manager.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import jplugman.manager.impl.state.PluginContext;
import lombok.NonNull;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ProvidedServiceTypeCollector implements Collector<PluginContext, Table<String, Integer, PluginIdAndVersionServiceClass>, Table<String, Integer, PluginIdAndVersionServiceClass>> {

    @Override
    public Supplier<Table<String, Integer, PluginIdAndVersionServiceClass>> supplier() {
        return HashBasedTable::create;
    }

    @Override
    public BiConsumer<Table<String, Integer, PluginIdAndVersionServiceClass>, PluginContext> accumulator() {
        return (t, p) -> PluginIdAndVersionServiceClass.createWith(p).ifPresent(v -> accumulate(t, v));
    }

    @Override
    public BinaryOperator<Table<String, Integer, PluginIdAndVersionServiceClass>> combiner() {
        return (t1, t2) -> {
            t2.values().forEach(v -> accumulate(t1, v));
            return t1;
        };
    }

    @Override
    public Function<Table<String, Integer, PluginIdAndVersionServiceClass>, Table<String, Integer, PluginIdAndVersionServiceClass>> finisher() {
        return t -> t;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

    private void accumulate(@NonNull Table<String, Integer, PluginIdAndVersionServiceClass> table,
                            @NonNull PluginIdAndVersionServiceClass toAdd) {
        final var serviceType = toAdd.getServiceType();
        final var majorVersion = toAdd.getMajorVersion();
        final var newValue = Optional.ofNullable(table.get(serviceType.getServiceName(), majorVersion))
                                     .map(existing -> max(existing, toAdd))
                                     .orElse(toAdd);
        table.put(serviceType.getServiceName(), majorVersion, newValue);
    }

    public @NonNull PluginIdAndVersionServiceClass max(@NonNull PluginIdAndVersionServiceClass pst1, @NonNull PluginIdAndVersionServiceClass pst2) {
        return pst1.getVersion().compareTo(pst2.getVersion()) >= 0 ? pst1 : pst2;
    }


}
