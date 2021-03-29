package Bastien Aracil.jplugman.manager;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ClassHierarchy {

    public static @NonNull Stream<Class<?>> streamHierarchy(@NonNull Class<?> root) {
        return new ClassHierarchy(root).streamHierarchy();
    }

    private final @NonNull Class<?> root;

    private final @NonNull Set<Class<?>> seen = new HashSet<>();

    private @NonNull Stream<Class<?>> streamHierarchy() {
        recurse(root);
        return seen.stream();
    }

    private void recurse(Class<?> root) {
        if (root == null || seen.contains(root)) {
            return;
        }
        seen.add(root);
        recurse(root.getSuperclass());
        Arrays.stream(root.getInterfaces())
              .forEach(this::recurse);
    }

}
