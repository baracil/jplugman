package jplugman.api;

import lombok.NonNull;

import java.util.Optional;

public interface Extension {

    /**
     * An instance of this extension
     */
    @NonNull Object getInstance();

    @NonNull Version getVersion();

    boolean provides(Requirement<?> requirement);

    default @NonNull Class<?> getType() {
        return getInstance().getClass();
    }

    default <T> @NonNull Optional<T> getInstanceAs(Class<T> clazz) {
        if (clazz.isInstance(getInstance())) {
            return Optional.of(clazz.cast(getInstance()));
        }
        return Optional.empty();
    }


}
