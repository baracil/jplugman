package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.state.PluginContext;

import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ProvidedServiceTypeCollector implements Collector<PluginContext, Table<Class<?>, Integer, PluginIdAndVersionServiceClass>, Table<Class<?>, Integer, PluginIdAndVersionServiceClass>> {

    @Override
    public Supplier<Table<Class<?>, Integer, PluginIdAndVersionServiceClass>> supplier() {
        return HashBasedTable::create;
    }

    @Override
    public BiConsumer<Table<Class<?>, Integer, PluginIdAndVersionServiceClass>, PluginContext> accumulator() {
        return (t, p) -> accumulate(t, PluginIdAndVersionServiceClass.createWith(p));
    }

    @Override
    public BinaryOperator<Table<Class<?>, Integer, PluginIdAndVersionServiceClass>> combiner() {
        return (t1, t2) -> {
            t2.values().forEach(v -> accumulate(t1, v));
            return t1;
        };
    }

    @Override
    public Function<Table<Class<?>, Integer, PluginIdAndVersionServiceClass>, Table<Class<?>, Integer, PluginIdAndVersionServiceClass>> finisher() {
        return t -> t;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

    private void accumulate(@NonNull Table<Class<?>, Integer, PluginIdAndVersionServiceClass> table,
                            @NonNull PluginIdAndVersionServiceClass toAdd) {
        final var serviceType = toAdd.getServiceType();
        final var majorVersion = toAdd.getMajorVersion();
        final var newValue = Optional.ofNullable(table.get(serviceType, majorVersion))
                                     .map(existing -> max(existing, toAdd))
                                     .orElse(toAdd);
        table.put(serviceType, majorVersion, newValue);
    }

    public @NonNull PluginIdAndVersionServiceClass max(@NonNull PluginIdAndVersionServiceClass pst1, @NonNull PluginIdAndVersionServiceClass pst2) {
        return pst1.getVersion().compareTo(pst2.getVersion()) >= 0 ? pst1 : pst2;
    }


}
