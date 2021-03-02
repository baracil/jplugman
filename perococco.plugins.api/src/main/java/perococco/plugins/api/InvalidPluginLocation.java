package Bastien Aracil.plugins.api;

import lombok.Getter;
import lombok.NonNull;

import java.nio.file.Path;

public class InvalidPluginLocation extends PeroPluginsException {

    @Getter
    private final @NonNull Path location;

    public InvalidPluginLocation(@NonNull String message, @NonNull Path location) {
        super(message);
        this.location = location;
    }
}
