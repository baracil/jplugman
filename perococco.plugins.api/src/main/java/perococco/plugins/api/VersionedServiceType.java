package Bastien Aracil.plugins.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class VersionedServiceType<T> implements Comparable<VersionedServiceType<?>>{

    /**
     * The type of the required service
     */
    @NonNull Class<T> serviceType;

    /**
     * The version of the required service
     */
    @NonNull Version version;

    public @NonNull Optional<T> castService(@NonNull VersionedService versionedService) {
        if (versionedService.getVersion().isCompatible(version)) {
            return versionedService.getServiceAs(serviceType);
        }
        return Optional.empty();
    }

    @Override
    public int compareTo(VersionedServiceType<?> that) {
        if (that.serviceType != this.serviceType) {
            return this.serviceType.getName().compareTo(that.serviceType.getName());
        }
        return this.version.compareTo(that.version);
    }

    @Override
    public String toString() {
        return "{" + serviceType + " "+version+"}";
    }

    public boolean provides(@NonNull VersionedServiceType<?> versionedService) {
        return this.serviceType.equals(versionedService.serviceType) && this.version.isCompatible(versionedService.version);
    }
}
