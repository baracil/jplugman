package Bastien Aracil.plugin.tools.impl;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;

public class ListTools {

    public static <T> @NonNull ImmutableList<T> addFirst(@NonNull ImmutableList<T> target, @NonNull T element) {
        return ImmutableList.<T>builder().add(element).addAll(target).build();
    }

    public static <T> @NonNull ImmutableList<T> removeFirst(@NonNull ImmutableList<T> target, @NonNull T element) {
        final var builder = ImmutableList.<T>builder();
        boolean removed = false;
        for (T t : target) {
            if (removed || !t.equals(element)) {
                builder.add(t);
            } else {
                removed = true;
            }
        }
        return builder.build();
    }
}
