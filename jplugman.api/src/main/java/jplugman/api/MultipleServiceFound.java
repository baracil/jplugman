package jplugman.api;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;

public class MultipleServiceFound extends Bastien AracilPluginsException {

    @Getter
    private final @NonNull Class<?> serviceType;
    private final @NonNull ImmutableList<String> availableServices;

    public <T> MultipleServiceFound(@NonNull Class<?> serviceType, @NonNull ImmutableList<String> availableServices) {
        super("Found multiple service implementing '"+serviceType+"' : "+String.join(", ", availableServices));
        this.serviceType = serviceType;
        this.availableServices = availableServices;
    }
}
