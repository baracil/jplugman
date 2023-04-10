package jplugman.test.lib;

import jplugman.test.lib._private.BasicCollectionSorter;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;

public interface CollectionSorter {

    <T extends Comparable<T>> List<T> sort(@NonNull Collection<T> input);

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
