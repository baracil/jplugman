package jplugman.api;

import lombok.Getter;
import lombok.NonNull;

public class IncompatiblePluginVersion extends Bastien AracilPluginsException{

    @Getter
    private final @NonNull Class<?> service;

    public IncompatiblePluginVersion(@NonNull Class<?> service) {
        super("The plugin is incompatible with the service provided by the application");
        this.service = service;
    }
}
