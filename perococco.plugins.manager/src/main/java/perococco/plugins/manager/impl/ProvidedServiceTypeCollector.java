package Bastien Aracil.plugins.manager.impl;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import lombok.NonNull;
import Bastien Aracil.plugins.manager.impl.state.PluginContext;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ProvidedServiceTypeCollector implements Collector<PluginContext, Table<Class<?>, Integer, ProvidedServiceType>, Table<Class<?>, Integer, ProvidedServiceType>> {

    @Override
    public Supplier<Table<Class<?>, Integer, ProvidedServiceType>> supplier() {
        return HashBasedTable::create;
    }

    @Override
    public BiConsumer<Table<Class<?>, Integer, ProvidedServiceType>, PluginContext> accumulator() {
        return (t, p) -> accumulate(t, ProvidedServiceType.create(p));
    }

    @Override
    public BinaryOperator<Table<Class<?>, Integer, ProvidedServiceType>> combiner() {
        return (t1, t2) -> {
            t2.values().forEach(v -> accumulate(t1, v));
            return t1;
        };
    }

    @Override
    public Function<Table<Class<?>, Integer, ProvidedServiceType>, Table<Class<?>, Integer, ProvidedServiceType>> finisher() {
        return t -> t;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }

    private void accumulate(@NonNull Table<Class<?>, Integer, ProvidedServiceType> table, @NonNull ProvidedServiceType providedServiceType) {
        final var serviceType = providedServiceType.getServiceType();
        final var majorVersion = providedServiceType.getMajorVersion();
        final var existing = table.get(serviceType, majorVersion);
        if (existing == null) {
            table.put(serviceType, majorVersion, providedServiceType);
        } else {
            table.put(serviceType, majorVersion, max(existing, providedServiceType));
        }
    }

    public @NonNull ProvidedServiceType max(@NonNull ProvidedServiceType pst1, @NonNull ProvidedServiceType pst2) {
        return pst1.getVersion().compareTo(pst2.getVersion()) >= 0 ?pst1:pst2;
    }



}
