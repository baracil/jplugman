package jplugman.manager.impl;

import jplugman.api.ApiVersion;
import jplugman.api.Requirement;
import lombok.NonNull;
import lombok.Value;

import java.util.Optional;

@Value
public class ServiceClass {

    @NonNull Class<?> type;

    @NonNull ApiVersion version;

    public String getServiceName() {
        return type.getName();
    }

    public boolean isCompatible(int majorVersion) {
        if (version.version() == majorVersion) {
            return true;
        }
        for (int retroVersion : version.retroVersions()) {
            if (retroVersion == majorVersion) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return type + "(v"+ version +")";
    }


    public static @NonNull Optional<ServiceClass> create(@NonNull Class<?> service) {
        final ApiVersion version = service.getAnnotation(ApiVersion.class);
        if (service.isInterface() && version != null) {
            return Optional.of(new ServiceClass(service, version));
        }
        return Optional.empty();
    }


    public boolean provides(@NonNull Requirement<?> requirement) {
        return type.equals(requirement.getServiceType()) && isCompatible(requirement.getApiVersion());
    }
}
