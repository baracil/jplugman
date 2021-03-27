package jplugman.api;

import lombok.Getter;
import lombok.NonNull;

public class InvalidPluginVersion extends Bastien AracilPluginsException {

    @Getter
    private final @NonNull Class<?> extensionType;

    public InvalidPluginVersion(@NonNull String message, @NonNull Class<?> extensionType) {
        super(message);
        this.extensionType = extensionType;
    }
}
