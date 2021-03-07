package Bastien Aracil.plugins.api;

import lombok.Getter;
import lombok.NonNull;

public class ServiceNotFound extends Bastien AracilPluginsException {

    @Getter
    private final @NonNull Class<?> serviceType;

    public <T> ServiceNotFound(@NonNull Class<?> serviceType) {
        super("Could not find service : '"+serviceType+"'");
        this.serviceType = serviceType;
    }
}
