package Bastien Aracil.plugins.api;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class VersionedService {

    private final @NonNull Object service;

    private final int version;

    public @NonNull <T> Optional<T> getServiceAs(@NonNull Class<T> serviceType) {
        if (serviceType.isInstance(service)) {
            return Optional.of(serviceType.cast(service));
        }
        return Optional.empty();
    }
}
