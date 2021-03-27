package jplugman.manager.impl;

import jplugman.api.Requirement;
import jplugman.api.Version;
import lombok.NonNull;
import lombok.Value;

@Value
public class VersionedServiceClass {

    /**
     * The service type
     */
    @NonNull ServiceClass serviceClass;

    /**
     * The require api version of service
     */
    @NonNull Version version;


    @Override
    public String toString() {
        return "{" + serviceClass + " "+version+"}";
    }

    public boolean provides(@NonNull Requirement<?> requirement) {
        return serviceClass.provides(requirement);
    }

}
