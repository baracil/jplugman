package jplugman.manager.impl;

import jplugman.api.Extension;
import jplugman.api.Requirement;
import jplugman.manager.MutableServiceProvider;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class HashSetServiceProvider implements MutableServiceProvider {

    private final Set<Extension> extensions = new HashSet<>();

    @Override
    public @NonNull <T> Stream<T> findService(@NonNull Requirement<T> requirement) {
        return extensions.stream()
                         .filter(e -> e.provides(requirement))
                         .map(e -> e.getInstanceAs(requirement.getServiceType()))
                         .flatMap(Optional::stream);
    }

    public void addExtension(@NonNull Extension extension) {
        this.extensions.add(extension);
    }


    public void removeExtension(@NonNull Extension extension) {
        this.extensions.remove(extension);
    }
}
