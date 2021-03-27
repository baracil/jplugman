package Bastien Aracil.jplugman.manager;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassHierarchy<T> {

    public static <T> @NonNull Stream<Class<? super T>> streamHierarchy(@NonNull Class<T> root) {
        return new ClassHierarchy<>(root).streamHierarchy();
    }

    private final @NonNull Class<T> root;

    private final @NonNull Set<Class<? super T>> seen = new HashSet<>();

    private @NonNull Stream<Class<? super T>> streamHierarchy() {
        recurse(root);
        return seen.stream();
    }

    private void recurse(Class<? super T> root) {
        if (root == null || seen.contains(root)) {
            return;
        }
        seen.add(root);
        recurse(root.getSuperclass());
        Arrays.stream(root.getInterfaces())
              .map(c -> (Class<? super T>)c)
              .forEach(this::recurse);
    }

}
