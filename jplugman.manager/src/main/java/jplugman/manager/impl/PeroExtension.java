package jplugman.manager.impl;

import jplugman.api.Extension;
import jplugman.api.Requirement;
import jplugman.api.Version;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PeroExtension implements Extension {

    public static @NonNull PeroExtension withProvidedService(@NonNull Object instance, @NonNull VersionedServiceClass providedService) {
        return new PeroExtension(instance, providedService);
    }

    public static @NonNull PeroExtension withoutProvidedService(@NonNull Object instance) {
        return new PeroExtension(instance, null);
    }

    /**
     * An instance of this extension
     */
    @Getter
    private final @NonNull Object instance;

    /**
     * the service provided by this extension
     */
    private final VersionedServiceClass providedService;



    public @NonNull Class<?> getType() {
        return instance.getClass();
    }

    public @NonNull Version getVersion() {
        return providedService.getVersion();
    }


    public <T> @NonNull Optional<T> getInstanceAs(Class<T> clazz) {
        if (clazz.isInstance(instance)) {
            return Optional.of(clazz.cast(instance));
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        if (providedService == null) {
            return "Service{"+instance.getClass()+"}";
        }
        return "Service{" + instance.getClass() + " : " + providedService+" }";
    }

    public boolean provides(Requirement<?> requirement) {
        return providedService.provides(requirement);
    }
}
