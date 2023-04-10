package jplugman.api;

import lombok.Getter;
import lombok.NonNull;

import java.util.List;

public class MultipleServiceFound extends JPlugmanException {

    @Getter
    private final @NonNull Class<?> serviceType;
    @Getter
    private final @NonNull List<String> availableServices;

    public MultipleServiceFound(@NonNull Class<?> serviceType, @NonNull List<String> availableServices) {
        super("Found multiple service implementing '"+serviceType+"' : "+String.join(", ", availableServices));
        this.serviceType = serviceType;
        this.availableServices = availableServices;
    }
}
