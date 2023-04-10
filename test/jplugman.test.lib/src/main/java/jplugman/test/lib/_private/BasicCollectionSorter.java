package jplugman.test.lib._private;

import jplugman.test.lib.CollectionSorter;
import lombok.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class BasicCollectionSorter implements CollectionSorter {

    @Override
    public <T extends Comparable<T>> List<T> sort(@NonNull Collection<T> input) {
        return input.stream().sorted().collect(Collectors.toList());
    }
}
