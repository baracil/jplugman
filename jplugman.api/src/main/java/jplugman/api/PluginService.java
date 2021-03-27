package jplugman.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PluginService<T> {

    /**
     * The service provided by the plugin
     */
    @Getter
    private final @NonNull T service;

    /**
     * If the service implement an extension point,
     * <code>extension</code> contains all the information
     * about it.
     */
    private final ExtensionData<? super T> extensionData;

    public @NonNull Optional<ExtensionData<? super T>> getExtensionData() {
        return Optional.ofNullable(extensionData);
    }

    public <U> @NonNull Optional<U> getServiceAs(@NonNull Class<U> clazz) {
        if (clazz.isInstance(service)) {
            return Optional.of(clazz.cast(service));
        }
        return Optional.empty();
    }

    public @NonNull Optional<Version> getVersion() {
        return Optional.ofNullable(extensionData).map(ExtensionData::getVersion);
    }

    public @NonNull Class<?> getServiceClass() {
        return service.getClass();
    }
}
