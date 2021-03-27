package jplugman.manager.impl;

import com.google.common.collect.ImmutableSet;
import jplugman.api.Extension;
import jplugman.api.Requirement;
import jplugman.api.ServiceProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class ImmutableServiceProvider implements ServiceProvider {

    /**
     * @param extensions the services
     * @return an immutable {@link ServiceProvider} that uses the provided services
     */
    public static @NonNull ServiceProvider of(@NonNull ImmutableSet<Extension> extensions) {
        return new ImmutableServiceProvider(extensions);
    }

    /**
     * @return an immutable {@link ServiceProvider} that provides no service
     */
    public static @NonNull ServiceProvider of() {
        return of(ImmutableSet.of());
    }

    private final @NonNull ImmutableSet<Extension> extensions;

    @Override
    public @NonNull <T> Stream<T> findService(@NonNull Requirement<T> requirement) {
        return extensions.stream()
                         .filter(e -> e.provides(requirement))
                         .map(e -> e.getInstanceAs(requirement.getServiceType()))
                         .flatMap(Optional::stream);
    }

}
