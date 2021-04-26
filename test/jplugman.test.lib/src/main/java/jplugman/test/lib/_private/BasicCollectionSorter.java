package jplugman.test.lib._private;

import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import jplugman.test.lib.CollectionSorter;
import lombok.NonNull;

public class BasicCollectionSorter implements CollectionSorter {

    @Override
    public <T extends Comparable<T>> ImmutableList<T> sort(@NonNull ImmutableCollection<T> input) {
        return input.stream().sorted().collect(ImmutableList.toImmutableList());
    }
}
