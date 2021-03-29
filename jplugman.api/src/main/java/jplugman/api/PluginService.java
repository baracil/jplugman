package jplugman.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class PluginService {

    /**
     * The service provided by the plugin
     */
    @Getter
    private final @NonNull Object service;

    /**
     * If the service implement an extension point,
     * <code>extensionData</code> contains all the information
     * about it.
     */
    private final ExtensionData extensionData;

    public @NonNull Optional<ExtensionData> getExtensionData() {
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

    @Override
    public String toString() {
        if (extensionData == null) {
            return "Plugin{"+service.getClass().getSimpleName()+"}";
        } else {
            return "Plugin{"+service.getClass().getSimpleName()+"  "+extensionData.getVersion()+"}";
        }
    }
}
