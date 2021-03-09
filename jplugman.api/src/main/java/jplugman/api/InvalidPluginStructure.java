package jplugman.api;

import lombok.Getter;
import lombok.NonNull;

import java.nio.file.Path;

public class InvalidPluginStructure extends Bastien AracilPluginsException {

    @Getter
    private final @NonNull Path location;

    public InvalidPluginStructure(@NonNull Path location) {
        super("Plugin has an invalid structure. Should be a zip containing jars");
        this.location = location;
    }
}
