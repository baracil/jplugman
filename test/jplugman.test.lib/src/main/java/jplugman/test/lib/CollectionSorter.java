package jplugman.test.lib;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import jplugman.test.lib._private.BasicCollectionSorter;
import lombok.NonNull;

import java.util.ServiceLoader;

public interface CollectionSorter {

    <T extends Comparable<T>> ImmutableList<T> sort(@NonNull ImmutableCollection<T> input);

    static @NonNull CollectionSorter create() {
        return create(ServiceLoader.load(CollectionSorter.class));
    }

    static @NonNull CollectionSorter create(@NonNull ModuleLayer moduleLayer) {
        return create(ServiceLoader.load(moduleLayer, CollectionSorter.class));
    }

    static private CollectionSorter create(@NonNull ServiceLoader<CollectionSorter> serviceLoad) {
        return serviceLoad.stream()
                          .map(ServiceLoader.Provider::get)
                          .findFirst()
                          .orElseGet(BasicCollectionSorter::new);

    }

}
