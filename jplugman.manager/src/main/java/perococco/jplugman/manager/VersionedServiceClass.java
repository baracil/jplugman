package Bastien Aracil.jplugman.manager;

import jplugman.api.Requirement;
import jplugman.api.Version;
import lombok.NonNull;
import lombok.Value;

@Value
public class VersionedServiceClass {

    /**
     * The service type
     */
    @NonNull ProvidedServiceExtractor.ExtensionPointData extensionPointData;

    /**
     * The require api version of service
     */
    @NonNull Version version;


    @Override
    public String toString() {
        return "{" + extensionPointData + " "+version+"}";
    }

    public boolean provides(@NonNull Requirement<?> requirement) {
        return extensionPointData.provides(requirement);
    }

}
