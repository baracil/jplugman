package jplugman.api;

import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class VersionedServiceClass<T> {

    /**
     * The class of the required service
     */
    @NonNull Class<T> serviceClass;

    /**
     * The version of the required service
     */
    @NonNull Version version;

    public int getMajorVersion() {
        return version.getMajor();
    }


    public @NonNull Optional<T> castService(@NonNull VersionedService versionedService) {
        if (versionedService.getVersion().isCompatible(version)) {
            return versionedService.getServiceAs(serviceClass);
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "{" + serviceClass + " "+version+"}";
    }

    public boolean provides(@NonNull VersionedServiceClass<?> versionedService) {
        return this.serviceClass.equals(versionedService.serviceClass) && this.version.isCompatible(versionedService.version);
    }

    public @NonNull VersionedService createVersionedService(Object service) {
        if (!serviceClass.isInstance(service)) {
            throw new IllegalArgumentException("The loaded service is not of the right type");
        }
        return new VersionedService(serviceClass,service,version);
    }
}
