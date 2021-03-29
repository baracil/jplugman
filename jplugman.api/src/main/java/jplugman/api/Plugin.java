package jplugman.api;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;

public interface Plugin {

    @NonNull Class<?> getServiceClass();

    /**
     * @return load the service provided by this plugin
     */
    @NonNull Object loadService(@NonNull ModuleLayer pluginLayer, @NonNull ServiceProvider serviceProvider);

    /**
     * @return the set of services this plugin needs to load the service it provides
     */
    @NonNull ImmutableSet<Requirement<?>> getRequirements();


}
