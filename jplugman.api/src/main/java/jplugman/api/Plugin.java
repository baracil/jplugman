package jplugman.api;

import lombok.NonNull;

import java.util.Set;

public interface Plugin {

    @NonNull Class<?> getServiceClass();

    /**
     * @return load the service provided by this plugin
     */
    @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);

    /**
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull Set<Requirement<?>> getRequirements();

    default void unloadService() {}

}
