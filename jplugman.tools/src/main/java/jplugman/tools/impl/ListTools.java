package jplugman.tools.impl;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListTools {

    public static <T> @NonNull List<T> addFirst(@NonNull List<T> target, @NonNull T element) {
        return Stream.concat(
            Stream.of(element),
            target.stream()
        ).collect(Collectors.toList());
    }

    public static <T> @NonNull List<T> removeFirst(@NonNull List<T> target, @NonNull T element) {
        final List<T> building = new ArrayList<>(target.size());

        boolean removed = false;
        for (T t : target) {
            if (removed || !t.equals(element)) {
                building.add(t);
            } else {
                removed = true;
            }
        }
        return building;
    }
}
